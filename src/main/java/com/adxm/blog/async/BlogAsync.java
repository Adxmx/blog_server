package com.adxm.blog.async;
import com.adxm.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BlogAsync {

  @Autowired
  private BlogService blogService;
  
  @Async
  public void viewIncrement(int id) {
    blogService.fieldIncrement("view", id);
  }

  @Async
  public void thumbIncrement(int id) {
    blogService.fieldIncrement("thumb", id);
  }
}
