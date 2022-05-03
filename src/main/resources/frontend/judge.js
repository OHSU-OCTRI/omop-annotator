import { createApp } from 'vue';

import JudgmentShell from './components/JudgmentShell';
import injectionKeys from './utils/injection-keys';

const rootNode = document.getElementById('judgment_app');
const { contextPath, csrfToken, csrfHeader, poolId, topicId } = rootNode.dataset;
const props = {
  poolId: poolId ? Number.parseInt(poolId) : null,
  topicId: topicId ? Number.parseInt(topicId) : null
};

createApp(JudgmentShell, props)
  .provide(injectionKeys.contextPath, contextPath)
  .provide(injectionKeys.csrfToken, csrfToken)
  .provide(injectionKeys.csrfHeader, csrfHeader)
  .mount(rootNode);
