package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class CouponController {

    private static List<Coupon> m_coupons = new ArrayList<>();

    // static ctor
    static {
        m_coupons.add( new Coupon(1, "Caffe") );
        m_coupons.add( new Coupon(2, "Movie VIP") );
        m_coupons.add( new Coupon(3, "Sky jump") );
    }

    @GetMapping("/coupon")
    public List<Coupon> getCoupons()
    {
        return m_coupons;
    }

    @GetMapping("/coupon/{id}")
    public Coupon doGetCouponById(@PathVariable("id") int id)
    {
        for(Coupon c : m_coupons)
        {
            if (c.getId() == id)
            {
                return c;
            }
        }
        return null;
    }

    @PostMapping("/coupon")
    public String addCoupon(@RequestBody Coupon c)
    {
        m_coupons.add(c);
        return "Coupon add";
    }

    @PutMapping("/coupon/{id}")
    public String doUpdateCouponByID(@PathVariable("id") int id,
                                     @RequestBody Coupon sent)
    {
        for (Coupon c : m_coupons) {
            if (c.getId() == id){
                c.setId(sent.getId());
                c.setTitle(sent.getTitle());
                return  "Coupon updated";
            }
        }
        return "Coupon not found";
    }

    @DeleteMapping ("/coupon/{id}")
    public String deleteCouponById(@PathVariable("id") int id)
    {
        Iterator<Coupon> it = m_coupons.iterator();
        while(it.hasNext()) {
            Coupon i = it.next();
            if(i.getId() == id) {
                it.remove();
                return "Coupon deleted";
            }
        }
        return "Coupon not found";
    }
}
