package com.example.payment.repositories;

import com.example.payment.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository  extends JpaRepository<Merchant,String> {
}
