<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button
          :preIcon="IconEnum.ADD"
          v-auth="StrategyAuth.ADD"
          @click="handleCreate"
          type="primary"
        >
          新增
        </a-button>
        <a-button
          :preIcon="IconEnum.DELETE"
          v-auth="StrategyAuth.DEL"
          @click="handleDelete"
          type="primary"
          color="error"
        >
          删除
        </a-button>
        <a-button
          :preIcon="IconEnum.RESET"
          v-auth="StrategyAuth.EDIT"
          @click="handleRefresh"
          type="primary"
          color="error"
        >
          刷新缓存
        </a-button>
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: IconEnum.VIEW,
              tooltip: '查看',
              auth: StrategyAuth.SINGLE,
              onClick: handleView.bind(null, record),
            },
            {
              icon: IconEnum.EDIT,
              tooltip: '编辑',
              auth: StrategyAuth.EDIT,
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: IconEnum.DELETE,
              tooltip: '删除',
              auth: StrategyAuth.DEL,
              color: 'error',
              onClick: handleDelete.bind(null, record),
            },
          ]"
        />
      </template>
    </BasicTable>
    <RefreshDrawer @register="refreshRegisterDrawer" @success="handleSuccess" />
    <DetailDrawer @register="detailRegisterDrawer" />
  </div>
</template>

<script setup lang="ts">
  import { reactive } from 'vue';
  import {
    delStrategyApi,
    listStrategyApi,
    refreshStrategyApi,
  } from '@/api/tenant/source/strategy.api';
  import { useMessage } from '@/hooks/web/useMessage';
  import { IconEnum } from '@/enums';
  import { BasicTable, TableAction, useTable } from '@/components/Table';
  import { StrategyAuth } from '@/auth/tenant/source';
  import { columns, searchFormSchema } from './data';
  import { useDrawer } from '@/components/Drawer';
  import DetailDrawer from './Detail.vue';
  import RefreshDrawer from './Drawer.vue';

  const { createMessage, createConfirm } = useMessage();
  const state = reactive<{
    ids: string[];
    idNames: string;
  }>({
    ids: [],
    idNames: '',
  });

  const [refreshRegisterDrawer, { openDrawer: refreshOpenDrawer }] = useDrawer();
  const [detailRegisterDrawer, { openDrawer: detailOpenDrawer }] = useDrawer();
  const [registerTable, { reload }] = useTable({
    title: '源策略组列表',
    api: listStrategyApi,
    striped: false,
    useSearchForm: true,
    rowKey: 'id',
    bordered: true,
    showIndexColumn: true,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
    },
    showTableSetting: true,
    tableSetting: {
      fullScreen: true,
    },
    actionColumn: {
      width: 220,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
    },
    rowSelection: {
      onChange: (selectedRowKeys, selectRows) => {
        state.ids = selectedRowKeys as string[];
        state.idNames = selectRows
          .map((item) => {
            return item.name;
          })
          .join(',');
      },
    },
  });

  /** 查看按钮 */
  function handleView(record: Recordable) {
    detailOpenDrawer(true, {
      record,
    });
  }

  /** 新增按钮 */
  function handleCreate() {
    refreshOpenDrawer(true, {
      isUpdate: false,
    });
  }

  /** 修改按钮 */
  function handleEdit(record: Recordable) {
    refreshOpenDrawer(true, {
      record,
      isUpdate: true,
    });
  }

  /** 删除按钮 */
  function handleDelete(record: Recordable) {
    const delIds = record.id || state.ids;
    const delNames = record.name || state.idNames;
    if (!record.id && state.ids.length === 0) {
      createMessage.warning('请选择要操作的数据！');
    } else {
      createConfirm({
        iconType: 'warning',
        title: '提示',
        content: '是否确定要删除' + delNames + '?',
        onOk: () =>
          delStrategyApi(delIds).then(() => {
            createMessage.success('删除' + delNames + '成功！');
            reload();
          }),
      });
    }
  }

  /** 刷新缓存按钮 */
  function handleRefresh() {
    refreshStrategyApi().then(() => createMessage.success('缓存刷新成功！'));
  }

  function handleSuccess() {
    reload();
  }
</script>
