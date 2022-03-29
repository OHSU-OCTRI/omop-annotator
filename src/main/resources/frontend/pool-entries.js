import { createApp } from 'vue';
import PoolEntries from './components/PoolEntries';

const dataset = document.querySelector('#pool_entries').dataset;
const app = createApp(PoolEntries, {
  contextPath: dataset.contextPath,
  poolId: dataset.poolId ? Number.parseInt(dataset.poolId) : null,
  topicId: dataset.topicId ? Number.parseInt(dataset.topicId) : null
});
app.mount('#pool_entries');
