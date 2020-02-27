package com.example.myapplication.services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activities.RecipeDetails;
import com.example.myapplication.entities.Product;
import com.example.myapplication.entities.Recipe;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    DatabaseHelper myDb;
    private Context context;
    public static ArrayList<Recipe> recipeArrayList;

    public CustomAdapter(Context context, ArrayList<Recipe> modelArrayList) {
        this.context = context;
        this.recipeArrayList = modelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return recipeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        myDb = new DatabaseHelper(context);
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.checkBox = convertView.findViewById(R.id.cb);
            holder.tvRecipe = (TextView) convertView.findViewById(R.id.recipe);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvRecipe.setText(recipeArrayList.get(position).getTitle());
        holder.tvRecipe.setTag(recipeArrayList.get(position).getId());

        boolean value = recipeArrayList.get(position).getSelected();
        String s = Boolean.toString(value);
        holder.checkBox.setChecked(value);

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempView = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = tempView.findViewById(R.id.recipe);
                Integer pos = (Integer) holder.checkBox.getTag();

                if (recipeArrayList.get(pos).getSelected()) {
                    recipeArrayList.get(pos).setSelected(false);
                    myDb.updateRecipe(recipeArrayList.get(pos));
                } else {
                    recipeArrayList.get(pos).setSelected(true);
                    myDb.updateRecipe(recipeArrayList.get(pos));
                }
            }
        });
        holder.tvRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) holder.tvRecipe.getTag();
                int detailsPosition = pos - 1;

                Intent intent = new Intent(context, RecipeDetails.class);
                Recipe recipe = new Recipe();

                recipe.setId(recipeArrayList.get(detailsPosition).getId());
                recipe.setTitle(recipeArrayList.get(detailsPosition).getTitle());
                recipe.setRecipeURL(recipeArrayList.get(detailsPosition).getRecipeURL());

                ArrayList<Product> recipeProducts = myDb.readRecipeProducts(recipe);
                recipe.setRecipeProducts(recipeProducts);
                String s = String.valueOf(recipe.getRecipeProducts());

                String title = recipe.getTitle();
                String url = recipe.getRecipeURL();
                intent.putExtra("RECIPEPRODUCTS", s);
                intent.putExtra("URL", url);
                intent.putExtra("TITLE", title);

                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvRecipe;
    }
}
