package com.example.draganddropone;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView textViewOne;
    private TextView textViewTwo;
    private TextView textViewThree;
    private TextView textViewFour;
    private TextView textViewFive;


    private LinearLayout choisesBasket;

    private LinearLayout dropSlotOne;
    private LinearLayout dropSlotThree;
    private LinearLayout dropSlotTwo;
    private LinearLayout dropSlotFour;
    private LinearLayout dropSlotFive;

    private String vasya = "Вася";
    private String mitya = "Митя";
    private String dima = "Дима";
    private String senya = "Сеня";
    private String fedya = "Федя";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        implementEvents();
    }

    //Find all views and set Tag to all draggable views
    private void findViews() {

        textViewOne = findViewById(R.id.hat_one);
        textViewOne.setTag(vasya);
        textViewOne.setText(vasya.substring(0,1));

        textViewTwo = findViewById(R.id.hat_two);
        textViewTwo.setTag(mitya);
        textViewTwo.setText(mitya.substring(0,1));

        textViewThree = findViewById(R.id.hat_three);
        textViewThree.setTag(dima);
        textViewThree.setText(dima.substring(0,1));

        textViewFour = findViewById(R.id.hat_four);
        textViewFour.setTag(senya);
        textViewFour.setText(senya.substring(0,1));

        textViewFive = findViewById(R.id.hat_five);
        textViewFive.setTag(fedya);
        textViewFive.setText(fedya.substring(0,1));
    }


    //Implement long click and drag listener
    private void implementEvents() {
        //add or remove any view that you don't want to be dragged

        textViewOne.setOnLongClickListener(this);
        textViewTwo.setOnLongClickListener(this);
        textViewThree.setOnLongClickListener(this);
        textViewFour.setOnLongClickListener(this);
        textViewFive.setOnLongClickListener(this);

        //add or remove any layout view that you don't want to accept dragged view
        choisesBasket = findViewById(R.id.choises_basket_layout);
        choisesBasket.setOnDragListener(this);

        dropSlotOne = findViewById(R.id.drop_slot_one);
        dropSlotOne.setOnDragListener(this);

        dropSlotTwo = findViewById(R.id.drop_slot_two);
        dropSlotTwo.setOnDragListener(this);

        dropSlotThree = findViewById(R.id.drop_slot_three);
        dropSlotThree.setOnDragListener(this);

        dropSlotFour = findViewById(R.id.drop_slot_four);
        dropSlotFour.setOnDragListener(this);

        dropSlotFive = findViewById(R.id.drop_slot_five);
        dropSlotFive.setOnDragListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        // Create a new ClipData.
        // This is done in two steps to provide clarity. The convenience method
        // ClipData.newPlainText() can create a plain text ClipData in one step.

        // Create a new ClipData.Item from the ImageView object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDrag(data//data to be dragged
                , shadowBuilder //drag shadow
                , view//local data about the drag and drop operation
                , 0//no needed flags
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.


    @Override
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.

                    //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);//set background color to your view

                    // Invalidate the view to force a redraw in the new tint
                    //  view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;
                }

                //todo Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                //findViewById(R.id.task_description_textview);
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.

//todo
                //view.getBackground().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.YELLOW, BlendModeCompat.SRC_ATOP));
                //view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                //todo
//                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint


                view.invalidate();

                return true;


            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
//                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();

                // Turns off any color tints
//                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();

                //todo if drop is successful owner.removeView(v)
                //remove the dragged view

                owner.removeView(v);
                //caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                LinearLayout container = (LinearLayout) view;
                //Add the dragged view
                container.addView(v);
                //finally set Visibility to VISIBLE
                v.setVisibility(View.VISIBLE);



                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
//todo

//                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                if (dropEventNotHandled(event)) {

                    View vi = (View) event.getLocalState();
                    vi.setVisibility(View.VISIBLE);
                }



//todo
//                v.setVisibility(View.VISIBLE);


                //Does a getResult(), and displays what happened.
//                if (event.getResult())
//                {
//                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
//                }
//
//                else
//                {
//                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
//                    View vi = (View) event.getLocalState();
//                    vi.setVisibility(View.VISIBLE);
//                }

                if(isOrderCorrect())
                {
                    Toast.makeText(this, "ВЕРНО! \nСпасибо, главный\nтестировщик Мила", Toast.LENGTH_SHORT).show();

                }
                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }


    private boolean dropEventNotHandled(DragEvent dragEvent) {

        return !dragEvent.getResult();

    }


    private boolean isOrderCorrect() {
        boolean isCorrect = false;
        //if left contains textviewone && middle contains ... => isCorrect = true;

        if(findViewById(R.id.drop_slot_one).findViewWithTag(mitya) != null
            && findViewById(R.id.drop_slot_two).findViewWithTag(vasya) != null
            && findViewById(R.id.drop_slot_three).findViewWithTag(fedya) != null
            && findViewById(R.id.drop_slot_four).findViewWithTag(dima) != null
            && findViewById(R.id.drop_slot_five).findViewWithTag(senya) != null)
        {
            isCorrect = true;
        }


        return isCorrect;
    }

}