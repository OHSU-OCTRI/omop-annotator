import { createApp } from 'vue';
import AnnotationLabels from './components/AnnotationLabels';

const dataset = document.querySelector('#annotation_labels').dataset;
const app = createApp(AnnotationLabels, {
  contextPath: dataset.contextPath,
  schemaId: dataset.schemaId ? Number.parseInt(dataset.schemaId) : null
});
app.mount('#annotation_labels');
