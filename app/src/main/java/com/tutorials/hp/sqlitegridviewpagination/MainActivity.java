package com.tutorials.hp.sqlitegridviewpagination;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.tutorials.hp.sqlitegridviewpagination.mAdapterView.CustomAdapter;
import com.tutorials.hp.sqlitegridviewpagination.mAdapterView.Paginator;
import com.tutorials.hp.sqlitegridviewpagination.mDB.Spacecraft;

public class MainActivity extends AppCompatActivity {

    GridView gv;
    EditText nameEditText,propellantEditTxt;
    Button saveBtn,retrieveBtn,nextBtn,prevBtn;

    private int currentPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //INITIALIZE VIEWS
        gv= (GridView) findViewById(R.id.gv);
        nextBtn= (Button) findViewById(R.id.nextBn);
        prevBtn= (Button) findViewById(R.id.prevBtn);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        prevBtn.setEnabled(false);

        //FIRST PAGE WHEN LOADED
        if(new Paginator().getCurrentSpacecrafts(currentPage).size()>0)
        {
            gv.setAdapter(new CustomAdapter(MainActivity.this,new Paginator().getCurrentSpacecrafts(currentPage)));

        }else
        {
            nextBtn.setEnabled(false);
        }


        //DISPLAY DIALOG
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });

        //NEXT BUTTON CLICKED
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage+=1;
                gv.setAdapter(new CustomAdapter(MainActivity.this,new Paginator().getCurrentSpacecrafts(currentPage)));
                toggleButtons();
            }
        });

        //PREVIOUS BUTTON CLICKED
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage-=1;
                gv.setAdapter(new CustomAdapter(MainActivity.this,new Paginator().getCurrentSpacecrafts(currentPage)));
                toggleButtons();
            }
        });

    }

    /*
    DISPLAY INPUT DIALOG
     */
    private void displayDialog()
    {
        final Dialog d=new Dialog(this);
        d.setTitle("SQLITE DATA");
        d.setContentView(R.layout.dialog_layout);

        nameEditText= (EditText) d.findViewById(R.id.nameEditTxt);
        propellantEditTxt= (EditText) d.findViewById(R.id.propellantEditTxt);

        saveBtn= (Button) d.findViewById(R.id.saveBtn);
        retrieveBtn= (Button) d.findViewById(R.id.retrieveBtn);


            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Spacecraft s=new Spacecraft();
                    save(s);
                    nameEditText.setText("");
                    propellantEditTxt.setText("");
                    toggleButtons();

                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gv.setAdapter(new CustomAdapter(MainActivity.this,new Paginator().getCurrentSpacecrafts(currentPage)));
                    toggleButtons();

                }
            });
       d.show();

    }

    /*
    INSERT DATA
     */
    private void save(Spacecraft s)
    {
        s.setName(nameEditText.getText().toString());
        s.setPropellant(propellantEditTxt.getText().toString());
        s.save();
        gv.setAdapter(new CustomAdapter(MainActivity.this,new Paginator().getCurrentSpacecrafts(currentPage)));
        toggleButtons();
    }

    /*
    TOGGLE PREVIOUS AND NEXT BUTTONS
     */
    private void toggleButtons()
    {
        if(currentPage==new Paginator().getTotalPages())
        {
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(true);
        }else
        if(currentPage==0)
        {
            prevBtn.setEnabled(false);
            nextBtn.setEnabled(true);
        }else
        if(currentPage>=1 && currentPage < new Paginator().getTotalPages())
        {
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(true);
        }

    }

}
