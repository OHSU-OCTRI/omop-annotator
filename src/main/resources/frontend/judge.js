import { createApp } from 'vue';

import JudgmentShell from './components/JudgmentShell';

const rootNode = document.getElementById('judgment_app');
const { contextPath, poolId, topicId } = rootNode.dataset;
createApp(JudgmentShell, {
  contextPath,
  poolId: poolId ? Number.parseInt(poolId) : null,
  topicId: topicId ? Number.parseInt(topicId) : null
}).mount(rootNode);
