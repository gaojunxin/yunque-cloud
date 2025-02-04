import { DictLM } from '@/model/sys';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';
import { isArray } from './core/ObjectUtil';
import { deepMerge } from './index';
import { cloneDeep, includes, isNil, merge } from 'lodash-es';

/** 字典转换 */
export function dictConvert(dictOptions: DictLM, val: string): string {
  if (!dictOptions) {
    console.error('字典不存在');
    return val;
  }
  const dict = dictOptions.find((dict) => dict.value === val);
  if (dict === undefined) {
    console.error('字典对应值不存在');
    return val;
  }
  return dict.label || '';
}

/** 字典选择式过滤 */
export function dictPick(dictOptions: DictLM, ...val: string[]): DictLM {
  if (!dictOptions) {
    console.error('字典不存在');
    return dictOptions;
  }
  if (!val) {
    console.error('无过滤值');
    return dictOptions;
  }
  return dictOptions.filter((dict) => includes(val, dict.value));
}

/** 字典排除式过滤 */
export function dictFilter(dictOptions: DictLM, ...val: string[]): DictLM {
  if (!dictOptions) {
    console.error('字典不存在');
    return dictOptions;
  }
  if (!val) {
    console.error('无过滤值');
    return dictOptions;
  }
  return dictOptions.filter((dict) => !includes(val, dict.value));
}

/** 字典转换 */
export function dictConversion(dictOptions: DictLM, val?: string) {
  if (isNil(val)) return undefined;
  if (!dictOptions) {
    console.error('字典不存在');
    return val;
  }
  const dict = dictOptions.find((dict) => dict.value === val);
  return dict !== undefined
    ? !dict.listClass
      ? dict.label
      : h(Tag, { color: dict.listClass }, () => dict.label)
    : val;
}

/**
 * 数据替换
 *
 *  @param source 主数据
 *  @param newData 更替数据
 */
export function sourceCopy(source: any, newData: any) {
  for (const key in newData) {
    // eslint-disable-next-line no-prototype-builtins
    if (source.hasOwnProperty(key)) {
      source[key] = newData[key];
    }
  }
}

/**
 * 数据赋值
 *
 *  @param source 主数据
 *  @param target 赋值数据
 */
export function sourceAssign(source: any, ...target: any) {
  let res: any = cloneDeep(source);
  if (isArray(target)) {
    let data = {};
    target.forEach((item) => {
      for (const key in item) {
        if (key.indexOf('.') !== -1) {
          data = key
            .split('.')
            .reverse()
            .reduce((p, c) => ({ [c]: p }), item[key]);
        } else {
          data = {};
          data[key] = item[key];
        }
        res = deepMerge(res, data);
      }
    });
  }
  return res;
}

/**
 * 设置对象指定字段值 | 考虑.
 *
 * @param info 主数据
 * @param value 赋值数据
 * @param field 赋值字段
 */
export function sourceAssignField(info: any, field?: string, value?: any) {
  if (info !== undefined && field !== undefined) {
    if (field.indexOf('.') !== -1) {
      const data = field
        .split('.')
        .reverse()
        .reduce((p, c) => ({ [c]: p }), value);
      merge(info, data);
    } else {
      info[field] = value;
    }
  }
}

/**
 * 日期格式化
 *
 *  @param time 时间
 *  @param pattern 日期格式
 */
export function parseTime(time: number | string, pattern?: string) {
  if (arguments.length === 0 || !time) {
    return null;
  }
  const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}';
  let date;
  if (typeof time === 'object') {
    date = time;
  } else {
    if (typeof time === 'string' && /^[0-9]+$/.test(time)) {
      time = parseInt(time);
    } else if (typeof time === 'string') {
      time = time.replace(new RegExp(/-/gm), '/');
    }
    if (typeof time === 'number' && time.toString().length === 10) {
      time = time * 1000;
    }
    date = new Date(time);
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay(),
  };
  return format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key];
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value];
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value;
    }
    return value || 0;
  });
}

/** 检验是否为移动端设备 */
export function isMobile() {
  return navigator.userAgent.match(
    /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i,
  );
}
