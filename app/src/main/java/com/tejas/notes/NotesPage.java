package com.tejas.notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class NotesPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView notesView = null;
    SimpleCursorAdapter simpleCursorAdapter = null;
    Cursor cursor = null;
    FloatingActionButton fab1 = null;
    DBHelper dbHelper;
    RelativeLayout relativeLayout = null;

    public static final String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesView = (ListView) findViewById(R.id.notesView);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);

        dbHelper = new DBHelper(this);
        cursor = dbHelper.getAllNotes();

        String[] columns = new String[]{
                DBHelper.NOTES_COLUMN_TITLE,
                DBHelper.NOTES_COLUMN_NOTE
        };
        int[] widgets = new int[]{
                R.id.medText,
                R.id.smallText
        };
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.card_view, cursor, columns, widgets, 0);
        notesView.setAdapter(simpleCursorAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view, int pos, long id) {

                Cursor itemCursor =(Cursor) NotesPage.this.notesView.getItemAtPosition(pos);
                int personID = itemCursor.getInt(itemCursor.getColumnIndex(DBHelper.NOTES_COLUM_ID));
                Intent i = new Intent(getApplicationContext(), AddNote.class);
                i.putExtra(KEY_EXTRA_CONTACT_ID, personID);
                startActivity(i);

            }
        });

        notesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {

                Snackbar snackbar = Snackbar.make(relativeLayout,"Note Deleted", Snackbar.LENGTH_LONG);
                snackbar.show();
                dbHelper.deleteNote((int) id);
                onResume();

                return true;
            }
        });
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
    public void onResume() {
        cursor = dbHelper.getAllNotes();
        simpleCursorAdapter.changeCursor(cursor);
        simpleCursorAdapter.notifyDataSetChanged();
        notesView.setAdapter(simpleCursorAdapter);

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes_page, menu);
        return true;
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addNote(View v) {
        Intent i = new Intent(this, AddNote.class);
        startActivity(i);
    }
}
