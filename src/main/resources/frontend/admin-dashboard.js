import { createApp } from 'vue';

import AdminDashboard from './components/AdminDashboard';
import injectionKeys from './utils/injection-keys';

const rootNode = document.getElementById('admin_dashboard');
const { contextPath, csrfToken, csrfHeader } = rootNode.dataset;
const props = {};

createApp(AdminDashboard, props)
  .provide(injectionKeys.contextPath, contextPath)
  .provide(injectionKeys.csrfToken, csrfToken)
  .provide(injectionKeys.csrfHeader, csrfHeader)
  .mount(rootNode);
