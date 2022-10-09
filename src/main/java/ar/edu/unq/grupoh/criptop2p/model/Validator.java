package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;

import java.util.regex.Pattern;

public class Validator {
    public static void patternMatches(String emailAddress) throws UserException {
        if (!(Pattern.compile("^(.+)@(\\S+)$").matcher(emailAddress).matches())){
            throw new UserException("Email not valid");
        }
    }

    public static void patternMatchesPassword(String password) throws UserException {
        if (!(Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$").matcher(password).matches())){
            throw new UserException("Password not valid");
        }
    }

    public static void nameMatches(String name) throws UserException{
        if (!(Pattern.compile("^.{3,30}").matcher(name).matches())){
            throw new UserException("Name or Lastname not valid");
        }
    }

    public static void addressMatches(String address) throws UserException{
        if (!(Pattern.compile("^.{3,30}").matcher(address).matches())){
            throw new UserException("Address not valid");
        }
    }

    public static void walletMatches(String wallet) throws UserException{
        if (!(Pattern.compile("[0-9]{8}").matcher(wallet).matches())){
            throw new UserException("Wallet not valid");
        }
    }

    public static void cvuMatches(String cvu) throws UserException{
        if (!(Pattern.compile("[0-9]{22}").matcher(cvu).matches())){
            throw new UserException("Cvu not valid");
        }
    }

}
