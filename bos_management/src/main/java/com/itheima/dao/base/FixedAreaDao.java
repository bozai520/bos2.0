package com.itheima.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itheima.domain.base.FixedArea;

public interface FixedAreaDao extends JpaRepository<FixedArea, String>,JpaSpecificationExecutor<FixedArea> {

}
