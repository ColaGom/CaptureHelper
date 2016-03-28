package com.aviv.capturehelper.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aviv.capturehelper.R;
import com.aviv.capturehelper.common.Dialoger;
import com.aviv.capturehelper.controller.AlbumDataLoader;
import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.dao.AlbumData;
import com.aviv.capturehelper.model.wrapper.WrapAlbumData;
import com.aviv.capturehelper.model.wrapper.WrapHelper;
import com.aviv.capturehelper.common.CaptureFloating;
import com.aviv.capturehelper.view.adapter.AdapterGridAlbum;
import com.aviv.capturehelper.view.dialog.CustomDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    @Bind(R.id.btn_plus)
    ImageButton mBtnPlus;
    @Bind(R.id.gv_album)
    GridView mGvAlbum;

    CustomDialog mDialog;
    AdapterGridAlbum mAdapter;
    List<WrapAlbumData> mAlbumDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAlbumDataList = WrapHelper.getAllAlbumData();
        mAdapter = new AdapterGridAlbum(this, R.layout.row_grid_album, mAlbumDataList);
        mGvAlbum.setAdapter(mAdapter);
        mGvAlbum.setOnItemClickListener(this);
        mGvAlbum.setOnItemLongClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setNavigationView();
    }

    private void setNavigationView()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        AlbumDataLoader loader = Master.getInstance().getAlbumDataLoader();
        String strCount = String.format("%s %d개 %s %d개", getString(R.string.label_album), loader.getAlbumCount()
                , getString(R.string.label_all_image), loader.getImageCount());

        TextView tvCount = (TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_count);
        tvCount.setText(strCount);
    }

    private void refresh()
    {
        mAdapter.clear();
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
           // startSettingActivity();
            startService(new Intent(this, CaptureFloating.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.btn_plus)
    void onClickPlusBtn(){
        startCreateAlbumActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WrapAlbumData albumData = (WrapAlbumData)parent.getItemAtPosition(position);

        if(!albumData.isEmpty())
            startAlbumActivity(albumData.getValue());
    }

    private View.OnClickListener getDeleteListener(WrapAlbumData data)
    {
        if(data.isEmpty()) return null;
        final AlbumData albumData = data.getValue();
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                String message = String.format("%s %s",getString(R.string.msg_selected_album_prefix), albumData.getName());
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

    private View.OnClickListener getModifyListener(WrapAlbumData data)
    {
        if(data.isEmpty()) return null;
        final AlbumData albumData = data.getValue();

        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        };
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        WrapAlbumData albumData = (WrapAlbumData)parent.getItemAtPosition(position);

        if(mDialog == null) {
            mDialog = new CustomDialog(this);
            mDialog.addButton(getString(R.string.label_delete), getDeleteListener(albumData));
            mDialog.addButton(getString(R.string.label_modify), getModifyListener(albumData));
        }

        mDialog.setTitle(albumData.getValue().getName());

        if(mDialog.isShowing())
            mDialog.dismiss();

        mDialog.show();

        return true;
    }
}
