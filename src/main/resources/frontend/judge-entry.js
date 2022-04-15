import { createApp } from 'vue';
import JudgeEntry from './components/JudgeEntry';
/**
 * In the controller add:
 *  model.put("pageScripts", new String[] { "vendor.js", "judge-entry.js" });
 *
 * In the mustache view:
 * <div id="judge_entry" data-context-path="{{req.contextPath}}" data-pool-entry-id={{poolEntry.id}} data-token={{_csrf.token}}></div>
 */
const dataset = document.querySelector('#judge_entry').dataset;
const app = createApp(JudgeEntry, {
    contextPath: dataset.contextPath,
    poolEntryId: Number.parseInt(dataset.poolEntryId),
    token: dataset.token
});
app.mount('#judge_entry');
