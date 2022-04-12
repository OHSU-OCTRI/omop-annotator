import { createApp } from 'vue';

import OmopBrowser from './components/OmopBrowser';

const rootNode = document.getElementById('omop_browser');
const dataset = rootNode.dataset;
createApp(OmopBrowser, {
  contextPath: dataset.contextPath,
  personId: dataset.personId ? Number.parseInt(dataset.personId) : null
}).mount(rootNode);
