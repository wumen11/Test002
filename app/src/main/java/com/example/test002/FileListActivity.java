package com.example.test002;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by tiankui on 16/7/30.
 */
public class FileListActivity extends FragmentActivity {

    private int fragmentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_file_list);
        if (getSupportFragmentManager().findFragmentById(R.id.rc_ac_fl_storage_folder_list_fragment) == null) {
            FileListFragment fileListFragment = new FileListFragment();
            showFragment(fileListFragment);
        }
    }

    public void showFragment(Fragment fragment) {
        fragmentCount++;
        getSupportFragmentManager()
        .beginTransaction()
        .addToBackStack(null)
        .replace(R.id.rc_ac_fl_storage_folder_list_fragment, fragment)
        .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (--fragmentCount == 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null && fragments.size() != 0) {
                for (Fragment fragment : fragments) {
                    if (fragment != null) {
                        fragment.onDestroy();
                    }
                }
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }
}
