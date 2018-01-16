package cn.czl.updatedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;

import ezy.boost.update.OnDownloadListener;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        update(false);
    }

    private void initViews() {
        ((TextView) findViewById(R.id.tv_version)).setText(AppUtils.getAppVersionName());
        findViewById(R.id.button).setOnClickListener(v -> update(true));
    }

    /**
     * 根据 agent.getInfo() 显示更新版本对话框，具体可参考 {@link UpdateAgent.DefaultUpdatePrompter}
     *
     * @param isManual
     */
    public void update(boolean isManual) {
        // 设置默认更新接口地址与渠道
        UpdateManager.setUrl("http://10.129.51.27:8080/app/check", "yyb");
        // 进入应用时查询更新
        UpdateManager.create(this)
                .setPrompter(agent -> {
                    UpdateInfo updateInfo = agent.getInfo();
                    String size = Formatter.formatShortFileSize(MainActivity.this, updateInfo.size);
                    new MaterialDialog.Builder(MainActivity.this)
                            .title("应用更新")
                            .content(R.string.update_content, updateInfo.versionName, size, updateInfo.updateContent)
                            .negativeText("以后再说")
                            .negativeColor(getResources().getColor(R.color.blue))
                            .positiveText("立即更新")
                            .positiveColor(getResources().getColor(R.color.blue))
                            .onPositive((dialog, which) -> agent.update())
                            .show();
                })
                .setOnFailureListener(error -> ToastUtils.showShort(error.toString()))
                .setOnDownloadListener(new OnDownloadListener() {
                    private MaterialDialog dialog;

                    @Override
                    public void onStart() {
                        dialog = new MaterialDialog.Builder(MainActivity.this)
                                .content("下载中")
                                .progress(false, 100, true)
                                .cancelable(false)
                                .show();
                    }

                    @Override
                    public void onProgress(int progress) {
                        if (null != dialog) {
                            dialog.setProgress(progress);
                        }
                    }

                    @Override
                    public void onFinish() {
                        if (dialog != null) {
                            dialog.dismiss();
                            dialog = null;
                        }
                    }
                })
                .setManual(isManual)
                .check();
    }
}
