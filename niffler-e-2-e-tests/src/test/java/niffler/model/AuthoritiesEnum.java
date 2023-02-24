package niffler.model;

public enum AuthoritiesEnum {

        READ("read"),
        WRITE("write");

        public final String AuthoritiesEnum;

        AuthoritiesEnum(String Authorities) {
            this.AuthoritiesEnum = Authorities;
        }

}
