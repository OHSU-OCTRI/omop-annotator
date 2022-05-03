import { createApp } from 'vue';
import JudgeControl from './components/JudgeControl';
/**
 * In the controller add:
 *  model.put("pageScripts", new String[] { "vendor.js", "judge-control.js" });
 *
 * In the mustache view:
 * <div id="judge_control" data-labels="{{labels}}"></div>
 */
const dataset = document.querySelector('#judge_control').dataset;
const app = createApp(JudgeControl, {
  labels: JSON.parse(dataset.labels)
});
app.mount('#judge_control');
