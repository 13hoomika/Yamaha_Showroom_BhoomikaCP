package com.bcp.yamaha.repository.admin;

import com.bcp.yamaha.entity.AdminEntity;

public interface AdminRepository {
    Boolean adminSave(AdminEntity adminEntity);
    AdminEntity findByName(String adminName);
    AdminEntity findById(int adminId);
    AdminEntity findByEmail(String email);

}
