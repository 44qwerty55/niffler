package niffler.model;


public enum SpendsCategories {

    Test("Test"),
    Restoran("���������"),
    Magazin("����������� ��������"),
    Study("�������� � QA.GURU ADVANCED");

    public final String spendsCategories;

    SpendsCategories(String spendsCategories) {
        this.spendsCategories = spendsCategories;
    }
}
