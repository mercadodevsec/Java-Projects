public class Dog extends Animal implements Action{

    public Dog() {}
//    Using Inheritance
    public Dog(String name, String type, String color, int numLegs) {
        super(name, type, color, numLegs);

    }
// Using Interface
    @Override
    public void run() {
        System.out.println("Running...");
    }
    @Override
    public void bite() {
        System.out.println("Biting...");
    }
}
