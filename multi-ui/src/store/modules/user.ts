import type { ErrorMessageMode } from '#/axios';
import { defineStore } from 'pinia';
import { store } from '@/store';
import {
  DicCommonPrivateEnum,
  PageEnum,
  ROLES_KEY,
  TenantTypeEnum,
  TOKEN_KEY,
  USER_INFO_KEY,
  UserTypeEnum,
} from '@/enums';
import { getAuthCache, setAuthCache } from '@/utils/auth';
import { useI18n } from '@/hooks/web/useI18n';
import { useMessage } from '@/hooks/web/useMessage';
import { router } from '@/router';
import { usePermissionStore } from '@/store/modules/permission';
import { RouteRecordRaw } from 'vue-router';
import { PAGE_NOT_FOUND_ROUTE } from '@/router/routes/basic';
import { isArray, isMap } from '@/utils/core/ObjectUtil';
import { h } from 'vue';
import { doLogout, getCodeImg, getUserInfo, oauthLoginApi } from '@/api/sys/login.api';
import { EnterpriseIM, UserIM } from '@/model/system/organize';
import { GetUserIM, LoginPM } from '@/model/sys';
import { MODULE_CACHE } from '@/enums/system/authority';

interface UserState {
  enterpriseInfo: Nullable<EnterpriseIM>;
  userInfo: Nullable<UserIM>;
  token?: string;
  expires_in?: number;
  roleList: string[];
  permCodeList: string[];
  routePathMap: Nullable<Map<string, string>>;
  sessionTimeout?: boolean;
  lastUpdateTime: number;
}

export const useUserStore = defineStore({
  id: 'app-user',
  state: (): UserState => ({
    // enterprise info
    enterpriseInfo: null,
    // user info
    userInfo: null,
    // token
    token: undefined,
    // expires_in
    expires_in: 0,
    // roleList
    roleList: [],
    // permCodeList
    permCodeList: [],
    // routePathMap
    routePathMap: null,
    // Whether the login expired
    sessionTimeout: false,
    // Last fetch time
    lastUpdateTime: 0,
  }),
  getters: {
    getEnterpriseInfo(): EnterpriseIM {
      return this.enterpriseInfo || ({} as EnterpriseIM);
    },

    getUserInfo(): UserIM {
      return this.userInfo || ({} as UserIM);
    },
    getToken(): string {
      return this.token || getAuthCache<string>(TOKEN_KEY);
    },
    getExpiresIn(): number {
      return this.expires_in as number;
    },
    getRoleList(): string[] {
      return this.roleList.length > 0 ? this.roleList : [];
    },
    getPermCodeList(): string[] {
      return this.permCodeList.length > 0 ? this.permCodeList : [];
    },
    getRoutePathMap(): Map<string, string> {
      return this.routePathMap !== null ? this.routePathMap : new Map<string, string>();
    },
    getSessionTimeout(): boolean {
      return !!this.sessionTimeout;
    },
    getLastUpdateTime(): number {
      return this.lastUpdateTime;
    },
    isNotLessor(): boolean {
      return !this.isLessor;
    },
    isLessor(): boolean {
      return this.enterpriseInfo?.isLessor === TenantTypeEnum.ADMIN;
    },
    isNotAdmin(): boolean {
      return !this.isAdmin;
    },
    isAdmin(): boolean {
      return this.userInfo?.userType === UserTypeEnum.ADMIN;
    },
  },
  actions: {
    setToken(info: string | undefined) {
      this.token = info ? info : ''; // for null or undefined value
      setAuthCache(TOKEN_KEY, info);
    },
    setExpiresIn(expires_in: number | undefined) {
      this.expires_in = expires_in ? expires_in : 0; // for null or undefined value
    },
    setRoleList(roleList: string[]) {
      this.roleList = roleList;
      setAuthCache(ROLES_KEY, roleList);
    },
    setPermCodeList(permCodeList: string[]) {
      this.permCodeList = permCodeList;
    },
    setRoutePathMap(routePathMap: Map<string, string> | null) {
      this.routePathMap = routePathMap;
    },
    setEnterpriseInfo(info: Nullable<EnterpriseIM>) {
      this.enterpriseInfo = info;
    },
    setUserInfo(info: Nullable<UserIM>) {
      this.userInfo = info;
      this.lastUpdateTime = new Date().getTime();
      setAuthCache(USER_INFO_KEY, info);
    },
    setSessionTimeout(flag: boolean) {
      this.sessionTimeout = flag;
    },
    useCommon(isCommon: string): boolean {
      return !(isCommon === DicCommonPrivateEnum.COMMON && this.isNotLessor);
    },
    resetState() {
      this.setToken(undefined);
      this.setSessionTimeout(false);
      this.setEnterpriseInfo(null);
      this.setUserInfo(null);
      this.setPermCodeList([]);
      this.setRoleList([]);
      this.setRoutePathMap(null);
      this.setExpiresIn(0);
    },
    getRoutePath(name: string, ...params: string[]) {
      if (this.routePathMap == null) {
        const { createMessage } = useMessage();
        createMessage.warning('请先登录！');
      } else {
        const path = this.routePathMap.get(name);
        if (path !== undefined) {
          params && params.length > 0 ? router.push(path + params.join('/')) : router.push(path);
        } else {
          const { createMessage } = useMessage();
          createMessage.warning('路由不存在！');
        }
      }
    },
    /**
     * @description: login
     */
    async login(
      params: LoginPM & {
        goHome?: boolean;
        mode?: ErrorMessageMode;
      },
    ): Promise<GetUserIM | null> {
      try {
        const { goHome = true, mode, ...loginParams } = params;
        const data = await oauthLoginApi(loginParams, mode);
        const { access_token } = data;

        // save token
        this.setToken(access_token);
        return this.afterLoginAction(goHome);
      } catch (error) {
        return Promise.reject(error);
      }
    },
    async afterLoginAction(goHome?: boolean): Promise<GetUserIM | null> {
      if (!this.getToken) return null;
      // get user info
      const getInfo = await this.getUserInfoAction();

      const sessionTimeout = this.sessionTimeout;
      if (sessionTimeout) {
        this.setSessionTimeout(false);
      } else {
        const permissionStore = usePermissionStore();

        // 动态路由加载（首次）
        if (!permissionStore.isDynamicAddedRoute) {
          const routes = await permissionStore.buildRoutesAction();
          [...routes, PAGE_NOT_FOUND_ROUTE].forEach((route) => {
            router.addRoute(route as unknown as RouteRecordRaw);
          });
          // 记录动态路由加载完成
          permissionStore.setDynamicAddedRoute(true);
        }

        goHome && (await router.replace(getInfo?.user?.homePath || PageEnum.BASE_HOME));
      }
      return getInfo;
    },

    async getUserInfoAction(): Promise<GetUserIM | null> {
      if (!this.getToken) return null;
      const getInfo = await getUserInfo();
      const { roles = [], permissions = [], routes, user, enterprise } = getInfo;
      if (isArray(roles)) {
        const roleList = roles as string[];
        this.setRoleList(roleList);
      } else {
        getInfo.roles = [];
        this.setRoleList([]);
      }
      if (isArray(permissions)) {
        const permCodeList = permissions as string[];
        this.setPermCodeList(permCodeList);
      } else {
        getInfo.permissions = [];
        this.setPermCodeList([]);
      }
      const routePathMap = new Map(Object.entries(routes));
      if (isMap(routePathMap)) {
        this.setRoutePathMap(routePathMap);
      }
      this.setUserInfo(user);
      this.setEnterpriseInfo(enterprise);
      return getInfo;
    },

    /**
     * @description: logout
     */
    async logout(goLogin = false) {
      if (this.getToken) {
        await doLogout().catch(() => {
          console.log('注销Token失败');
        });
      }
      sessionStorage.removeItem(MODULE_CACHE);
      this.resetState();
      usePermissionStore().resetState();
      if (goLogin) {
        // 直接回登陆页
        router.replace(PageEnum.BASE_LOGIN);
      } else {
        // 回登陆页带上当前路由地址
        router.replace({
          path: PageEnum.BASE_LOGIN,
          query: {
            redirect: encodeURIComponent(router.currentRoute.value.fullPath),
          },
        });
      }
    },

    /**
     * @description: get code img
     */
    async getCodeImage() {
      return await getCodeImg();
    },

    /**
     * @description: Confirm before logging out
     */
    confirmLoginOut() {
      const { createConfirm } = useMessage();
      const { t } = useI18n();
      createConfirm({
        iconType: 'warning',
        title: () => h('span', t('sys.app.logoutTip')),
        content: () => h('span', t('sys.app.logoutMessage')),
        onOk: async () => {
          // 主动登出，不带redirect地址
          await this.logout(true);
        },
      });
    },
  },
});

// Need to be used outside the setup
export function useUserStoreWithOut() {
  return useUserStore(store);
}
