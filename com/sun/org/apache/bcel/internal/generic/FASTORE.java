package com.sun.org.apache.bcel.internal.generic;

public class FASTORE extends ArrayInstruction implements StackConsumer {
  public FASTORE() { super((short)81); }
  
  public void accept(Visitor paramVisitor) {
    paramVisitor.visitStackConsumer(this);
    paramVisitor.visitExceptionThrower(this);
    paramVisitor.visitTypedInstruction(this);
    paramVisitor.visitArrayInstruction(this);
    paramVisitor.visitFASTORE(this);
  }
}


/* Location:              D:\software\jd-gui\jd-gui-windows-1.6.3\rt.jar!\com\sun\org\apache\bcel\internal\generic\FASTORE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */