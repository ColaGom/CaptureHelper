package com.aviv.capturehelper.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.CaptureService;
import com.aviv.capturehelper.common.Dialoger;
import com.aviv.capturehelper.controller.AlbumDataLoader;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.dao.AlbumData;
import com.aviv.capturehelper.model.wrapper.WrapAlbumData;
import com.aviv.capturehelper.model.wrapper.WrapHelper;
import com.aviv.capturehelper.view.adapter.AdapterGridAlbum;
import com.aviv.capturehelper.view.dialog.CustomDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @Bind(R.id.gv_album)
    GridView mGvAlbum;
    @Bind(R.id.nav_view)
    NavigationView mNaviView;
    @Bind(R.id.sc_enable)
    SwitchCompat mScEnable;

    CustomDialog mDialog;
    AdapterGridAlbum mAdapter;
    List<WrapAlbumData> mAlbumDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!TextUtils.isEmpty(Master.getInstance().getUserPrefLoader().getLockPattern())){
            startLockValidActivity();
        }

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGvAlbum.setOnItemClickListener(this);
        mGvAlbum.setOnItemLongClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                updateNavigationView();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setNavigationView();

        requestOrStartService();
    }

    private void startCaptureService()
    {
        Intent intent = new Intent(this, CaptureService.class);
        startService(intent);
    }

    private void requestOrStartService()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermissionGrant()){
                startCaptureService();
            }else
                requestPermission();
        }else{
            startCaptureService();
        }
    }

    private final String[] permissions = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE };

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 0);
    }

    private boolean checkPermissionGrant()
    {
        for(String permission:permissions)
        {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        requestOrStartService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, CaptureService.class);
        startService(intent);
    }

    private  void updateNavigationView()
    {
        AlbumDataLoader loader = Master.getInstance().getAlbumDataLoader();
        String strCount = String.format("%s %d개 %s %d개", getString(R.string.label_album), loader.getAlbumCount()
                , getString(R.string.label_all_image), loader.getImageCount());

        TextView tvCount = (TextView) mNaviView.findViewById(R.id.tv_count);
        tvCount.setText(strCount);

        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getBlockCount() * blockSize;
        long availableSize = statFs.getAvailableBlocks() * blockSize;

        float totalGB = totalSize / (float) (1024 * 1024 * 1024);
        float emptyGB = (totalSize - availableSize) / (float) (1024 * 1024 * 1024);
        float wDiff = emptyGB / totalGB;

        View backView = mNaviView.findViewById(R.id.v_back_storage);

        TextView tvStorage = (TextView) mNaviView.findViewById(R.id.tv_storage);
        View vForeStorage = mNaviView.findViewById(R.id.v_fore_storage);

        ViewGroup.LayoutParams params = vForeStorage.getLayoutParams();
        params.width = (int) (backView.getWidth() * wDiff);
        vForeStorage.setLayoutParams(params);
        tvStorage.setText(String.format("%.2fGB / %.2fGB", emptyGB, totalGB));
    }

    private void setNavigationView() {
        mNaviView.setNavigationItemSelectedListener(this);
        mScEnable.setChecked(Master.getInstance().getUserPrefLoader().getEnableHelper());
        updateNavigationView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        mAlbumDataList = WrapHelper.getAllAlbumData();
        mAdapter = new AdapterGridAlbum(this, R.layout.row_grid_album, mAlbumDataList);
        mGvAlbum.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.fab_plus)
    void onClickBtnPlus()
    {
        startCreateAlbumActivity();
    }


    @OnClick({R.id.btn_setting, R.id.sc_enable})
    void onClickNavItem(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_setting:
                startSettingActivity();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.sc_enable:
                Master.getInstance().getUserPrefLoader().putEnableHelper(mScEnable.isChecked());
                break;
        }
    }


    @Override
    protected void finishedAlbumCreate(int resultCode) {
        super.finishedAlbumCreate(resultCode);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.album_list, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setting) {
            startSettingActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WrapAlbumData albumData = (WrapAlbumData) parent.getItemAtPosition(position);

        if (!albumData.isEmpty()) {
            AlbumData value = albumData.getValue();
            if(value.getIsnew()) {
                value.setIsnew(false);
                Master.getInstance().getAlbumDataLoader().insert(value);
            }
            startAlbumActivity(value);
        }
    }

    private View.OnClickListener getDeleteListener(WrapAlbumData data) {
        if (data.isEmpty()) return null;
        final AlbumData albumData = data.getValue();
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                String message = String.format("%s %s", getString(R.string.msg_selected_album_prefix), albumData.getName());
                Dialoger.showAlertDialog(MainActivity.this, getString(R.string.title_delete_album), message, new Dialoger.AlertListener() {
                    @Override
                    public void onClickYes() {

                    }

                    @Override
                    public void onClickNo() {

                    }
                });
            }
        };
    }

    private View.OnClickListener getModifyListener(WrapAlbumData data) {
        if (data.isEmpty()) return null;
        final AlbumData albumData = data.getValue();

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        };
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        WrapAlbumData albumData = (WrapAlbumData) parent.getItemAtPosition(position);

        if (mDialog == null) {
            mDialog = new CustomDialog(this);
            mDialog.addButton(getString(R.string.label_delete), getDeleteListener(albumData));
            mDialog.addButton(getString(R.string.label_modify), getModifyListener(albumData));
        }

        mDialog.setTitle(albumData.getValue().getName());

        if (mDialog.isShowing())
            mDialog.dismiss();

        mDialog.show();

        return true;
    }
}
