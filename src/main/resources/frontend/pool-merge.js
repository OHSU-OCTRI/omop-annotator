import { createApp } from 'vue';

import PoolMerge from './components/PoolMerge';
import injectionKeys from './utils/injection-keys';

const rootNode = document.getElementById('pool_merge');
const { contextPath, csrfToken, csrfHeader } = rootNode.dataset;
const props = {};

createApp(PoolMerge, props)
  .provide(injectionKeys.contextPath, contextPath)
  .provide(injectionKeys.csrfToken, csrfToken)
  .provide(injectionKeys.csrfHeader, csrfHeader)
  .mount(rootNode);
