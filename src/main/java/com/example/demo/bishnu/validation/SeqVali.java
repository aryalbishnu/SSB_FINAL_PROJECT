package com.example.demo.bishnu.validation;

import javax.validation.GroupSequence;

import com.example.demo.bishnu.validation.SeqValiInterface.Advance;
import com.example.demo.bishnu.validation.SeqValiInterface.Basic;

public class SeqVali {
@GroupSequence({Basic.class, Advance.class})
public interface All{
  
}
  

}
