import { FormSchema } from '@/components/Form';
import { BasicColumn } from '@/components/Table';
import { DescItem } from '@/components/Description';
import { dicDictList } from '@/api/sys/dict.api';
import { dictConversion } from '@/utils/xueyi';
import { ColorEnum, DicCodeEnum, DicSortEnum, DicStatusEnum } from '@/enums';
import { StrategyIM } from '@/model/tenant/source';
import { TenantIM } from '@/model/tenant/tenant';
import { listSourceApi } from '@/api/tenant/source/source.api';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';
import { isNotEmpty } from '@/utils/core/ObjectUtil';
import { Tinymce } from '@/components/Tinymce';
import { DicSourceCodeEnum } from '@/enums/tenant/source';

/** 字典查询 */
export const dictMap = await dicDictList([
  DicCodeEnum.SYS_YES_NO,
  DicCodeEnum.SYS_NORMAL_DISABLE,
  DicSourceCodeEnum.TE_STRATEGY_SOURCE_TYPE,
]);

/** 字典表 */
export const dict: any = {
  DicYesNoOptions: dictMap[DicCodeEnum.SYS_YES_NO],
  DicNormalDisableOptions: dictMap[DicCodeEnum.SYS_NORMAL_DISABLE],
  DicSourceTypeOptions: dictMap[DicSourceCodeEnum.TE_STRATEGY_SOURCE_TYPE],
};

/** 表格数据 */
export const columns: BasicColumn[] = [
  {
    title: '策略名称',
    dataIndex: 'name',
    width: 130,
  },
  {
    title: '数据源Id',
    dataIndex: 'sourceId',
    width: 100,
  },
  {
    title: '数据源编码',
    dataIndex: 'sourceSlave',
    customRender: ({ record }) => {
      const data = record as StrategyIM;
      return h(Tag, { color: ColorEnum.ORANGE }, () => data.sourceSlave);
    },
    width: 100,
  },
  {
    title: '状态',
    dataIndex: 'status',
    width: 80,
    customRender: ({ record }) => {
      const data = record as StrategyIM;
      return dictConversion(dict.DicNormalDisableOptions, data.status);
    },
  },
  {
    title: '默认策略',
    dataIndex: 'isDefault',
    width: 100,
    customRender: ({ record }) => {
      const data = record as TenantIM;
      return dictConversion(dict.DicYesNoOptions, data.isDefault);
    },
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 140,
  },
];

/** 查询数据 */
export const searchFormSchema: FormSchema[] = [
  {
    label: '策略名称',
    field: 'name',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '数据源',
    field: 'sourceId',
    component: 'ApiSelect',
    componentProps: {
      api: listSourceApi,
      params: { status: DicStatusEnum.NORMAL },
      showSearch: true,
      optionFilterProp: 'label',
      resultField: 'items',
      labelField: 'name',
      valueField: 'id',
    },
    colProps: { span: 6 },
  },
  {
    label: '状态',
    field: 'status',
    component: 'Select',
    componentProps: {
      options: dict.DicNormalDisableOptions,
      showSearch: true,
      optionFilterProp: 'label',
    },
    colProps: { span: 6 },
  },
];

/** 表单数据 */
export const formSchema: FormSchema[] = [
  {
    label: '策略Id',
    field: 'id',
    component: 'Input',
    show: false,
    colProps: { span: 12 },
  },
  {
    label: '策略名称',
    field: 'name',
    component: 'Input',
    required: true,
    colProps: { span: 12 },
  },
  {
    label: '数据源',
    field: 'sourceId',
    component: 'ApiSelect',
    componentProps: {
      api: listSourceApi,
      params: { status: DicStatusEnum.NORMAL },
      showSearch: true,
      optionFilterProp: 'label',
      resultField: 'items',
      labelField: 'name',
      valueField: 'id',
    },
    dynamicDisabled: ({ values }) => isNotEmpty(values.id),
    required: true,
    colProps: { span: 12 },
  },
  {
    label: '显示顺序',
    field: 'sort',
    component: 'InputNumber',
    defaultValue: DicSortEnum.ZERO,
    colProps: { span: 12 },
  },
  {
    label: '状态',
    field: 'status',
    component: 'RadioButtonGroup',
    defaultValue: DicStatusEnum.NORMAL,
    componentProps: {
      options: dict.DicNormalDisableOptions,
    },
    colProps: { span: 12 },
  },
  {
    label: '备注',
    field: 'remark',
    component: 'InputTextArea',
    colProps: { span: 24 },
  },
];

/** 详情数据 */
export const detailSchema: DescItem[] = [
  {
    label: '策略名称',
    field: 'name',
    span: 12,
  },
  {
    label: '数据源Id',
    field: 'sourceId',
    span: 12,
  },
  {
    label: '数据源编码',
    field: 'sourceSlave',
    span: 12,
  },
  {
    label: '显示顺序',
    field: 'sort',
    span: 12,
  },
  {
    label: '状态',
    field: 'status',
    render: (val) => {
      return dictConversion(dict.DicNormalDisableOptions, val);
    },
    span: 12,
  },
  {
    label: '创建时间',
    field: 'createTime',
    span: 12,
  },
  {
    label: '备注',
    field: 'remark',
    render: (val) => {
      return h(Tinymce, {
        value: val,
        showImageUpload: false,
        options: {
          readonly: true,
          statusbar: false,
          toolbar: false,
          menubar: false,
        },
      });
    },
    span: 24,
  },
];
