//package ar.edu.unq.grupoh.criptop2p.model;
//
//import ar.edu.unq.grupoh.criptop2p.model.state.StateTransaction;
//import ar.edu.unq.grupoh.criptop2p.model.state.WaitingTransferMoney;
//import org.hibernate.HibernateException;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.usertype.UserType;
//
//import java.io.Serializable;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class TransactionStateType implements UserType {
//
//
//    @Override
//    public int[] sqlTypes() {
//        return new int[0];
//    }
//
//    @Override
//    public Class returnedClass() {
//        return StateTransaction.class;
//    }
//
//    @Override
//    public boolean equals(Object x, Object y) throws HibernateException {
//        return false;
//    }
//
//    @Override
//    public int hashCode(Object x) throws HibernateException {
//        return 0;
//    }
//
//    @Override
//    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
//        String name = rs.getString(names[0]);
//        switch (name) {
//            case WaitingTransferMoney.APPROVED_NAME:
//                return new PurchaseOrderStateApproved();
//
//            case PurchaseOrderStateStarted.STARTED_NAME:
//                return new PurchaseOrderStateStarted();
//
//            default:
//                throw new RuntimeException("Purchase Order state is corruputed");
//        }
//
//    }
//
//    @Override
//    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
//
//    }
//
//    @Override
//    public Object deepCopy(Object value) throws HibernateException {
//        return null;
//    }
//
//    @Override
//    public boolean isMutable() {
//        return false;
//    }
//
//    @Override
//    public Serializable disassemble(Object value) throws HibernateException {
//        return null;
//    }
//
//    @Override
//    public Object assemble(Serializable cached, Object owner) throws HibernateException {
//        return null;
//    }
//
//    @Override
//    public Object replace(Object original, Object target, Object owner) throws HibernateException {
//        return null;
//    }
//}
