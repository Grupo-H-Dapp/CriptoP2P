package ar.edu.unq.grupoh.criptop2p.model.enums;

/*
  Ongoing es cuando esta recien creadad/iniciada la Operacion
  Canceled es cuando fue cancelada tanto por el sistema como por el usuario
  Completed es cuando se completo correctamente

  ONGOING -> CANCELED
  ONGOING -> WAITING_CONFIRM -> COMPLETED
  ONGOING -> WAITING_CONFIRM -> CANCELED ?
 */
public enum StateTransaction {
    WAITING_TRANSFER_MONEY,WAITING_CONFIRM_TRANSFER_MONEY,WAITING_TRANSFER_CRYPTO,WAITING_CONFIRM_TRANSFER_CRYPTO,CANCELED,COMPLETED
}
