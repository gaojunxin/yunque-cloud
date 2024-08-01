<script setup lang="ts">
  import { h } from 'vue';
  import { Modal } from 'ant-design-vue';

  Modal.info({
    title: '新版本发布',
    content: h('div', {}, [h('p', 'MultiSaas v3.3.4 预览版本已发布')]),
    onOk() {
      handleClick();
    },
    okText: '前往体验新版',
  });

  function handleClick() {
    window.open('https://doc.xueyitt.cn', '_blank');
  }
</script>
<template>
  <div>
    <a-button type="primary" @click="handleClick">前往体验新版</a-button>
  </div>
</template>
