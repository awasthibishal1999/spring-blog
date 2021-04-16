package com.awasthi.bishal.Springblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.awasthi.bishal.Springblog.model.Post;



public interface PostRepo extends JpaRepository<Post, Long>  {

}
