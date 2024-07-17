package com.example.project183.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project183.Adapter.CartAdapter;
import com.example.project183.Helper.ChangeNumberItemsListener;
import com.example.project183.Helper.ManagmentCart;
import com.example.project183.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    ActivityCartBinding binding;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        calculateCart();
        initCartList();
    }

    private void initCartList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        }else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);

        }
        binding.cartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(),managmentCart , () -> calculateCart()));
    }

    private void calculateCart() {
        double percentTax = 0.02;  //percent 2% tax
        double delivery = 10;  //10 Dollar
        double tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100;
        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.totalFeeTxt.setText("₱" + itemTotal);
        binding.taxTxt.setText("₱" + tax);
        binding.deliveryTxt.setText("₱" + delivery);
        binding.totalTxt.setText("₱" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> startActivity(new Intent(CartActivity.this,MainActivity.class)));
    }
}