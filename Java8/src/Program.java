import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.time.*;
import java.time.temporal.*;

public class Program {
	public static void main(String[] args) {
		// Interface using default method implementation
		Formula formula = new Formula() {
		    @Override
		    public double calculate(int a) {
		        return sqrt(a * 100);
		    }
		};

		formula.calculate(100);
		formula.sqrt(16);
		
		
		// Using lambda expression syntax
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

		// Old way to sort a collection
		Collections.sort(names, new Comparator<String>() {
		    @Override
		    public int compare(String a, String b) {
		        return b.compareTo(a);
		    }
		});
		
		// New way
		Collections.sort(names, (a, b) -> b.compareTo(a));
		
		
		// Using the new :: operator
		Converter<String, Integer> converter = Integer::valueOf;
		Integer converted = converter.convert("123");
		System.out.println(converted);
		
		PersonFactory<Person> personFactory = Person::new;
		Person person = personFactory.create("Peter", "Parker");
		System.out.println(person.firstName + " " + person.lastName);
		
		
		// New functional interfaces
		Predicate<String> predicate = s -> s.length() > 0;
		predicate.test("foo");              // true
		predicate.negate().test("foo");     // false
		
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);
		backToString.apply("123");     // "123"
		
		Supplier<Person> personSupplier = Person::new;
		personSupplier.get();
		
		Consumer<Person> greeter = p -> System.out.println("Hello, " + p.firstName);
		greeter.accept(new Person("Luke", "Skywalker"));
		
		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
		Person p1 = new Person("John", "Doe");
		Person p2 = new Person("Alice", "Wonderland");
		comparator.compare(p1, p2);             // > 0
		comparator.reversed().compare(p1, p2);  // < 0
		
		Optional<String> optional = Optional.of("bam");
		optional.isPresent();           // true
		optional.get();                 // "bam"
		optional.orElse("fallback");    // "bam"
		optional.ifPresent(s -> System.out.println(s.charAt(0)));
		
		
		// Streams
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		
		// Filter
		stringCollection
	    	.stream()
	    	.filter(s -> s.startsWith("a"))
	    	.forEach(System.out::println);
		
		// Sort
		stringCollection
	    	.stream()
	    	.sorted()
	    	.filter(s -> s.startsWith("a"))
	    	.forEach(System.out::println);
		
		// Map
		stringCollection
	    	.stream()
	    	.map(String::toUpperCase)
	    	.sorted((a, b) -> b.compareTo(a))
	    	.forEach(System.out::println);
		
		// Match
		boolean anyStartsWithA = 
			stringCollection
				.stream()
				.anyMatch(s -> s.startsWith("a"));
		System.out.println(anyStartsWithA);

		boolean allStartsWithA = 
			stringCollection
				.stream()
				.allMatch(s -> s.startsWith("a"));

		System.out.println(allStartsWithA);
		boolean noneStartsWithZ = 
			stringCollection
				.stream()
				.noneMatch(s -> s.startsWith("z"));
		System.out.println(noneStartsWithZ);
		
		
		// Count
		long startsWithB =
			stringCollection
				.stream()
				.filter(s -> s.startsWith("b"))
				.count();
		System.out.println(startsWithB);
		
		// Reduce
		Optional<String> reduced =
			stringCollection
				.stream()
			    .sorted()
			    .reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent(System.out::println);
		
		
		// Parallel streams
		int max = 1000000;
		List<String> values = new ArrayList<>(max);
		
		for (int i = 0; i < max; i++) {
		    UUID uuid = UUID.randomUUID();
		    values.add(uuid.toString());
		}
		
		// Sequential sort
		long s0 = System.nanoTime();

		long sCount = values.stream().sorted().count();
		System.out.println(sCount);

		long s1 = System.nanoTime();

		long sTime = TimeUnit.NANOSECONDS.toMillis(s1 - s0);
		System.out.println(String.format("sequential sort took: %d ms", sTime));
		
		// Parallel sort
		long t0 = System.nanoTime();

		long pCount = values.parallelStream().sorted().count();
		System.out.println(pCount);

		long t1 = System.nanoTime();

		long pTime = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("parallel sort took: %d ms", pTime));
		
		
		// New map functionality
		Map<Integer, String> map = new HashMap<>();

		for (int i = 0; i < 10; i++) {
		    map.putIfAbsent(i, "val" + i);
		}

		map.forEach((id, val) -> System.out.println(val));
		
		map.computeIfPresent(3, (num, val) -> val + num);
		map.get(3);             // val33

		map.computeIfPresent(9, (num, val) -> null);
		map.containsKey(9);     // false

		map.computeIfAbsent(23, num -> "val" + num);
		map.containsKey(23);    // true

		map.computeIfAbsent(3, num -> "bam");
		map.get(3); 
		
		map.remove(3, "val3");
		map.get(3);             // val33

		map.remove(3, "val33");
		map.get(3); 
		
		map.getOrDefault(42, "not found");  // not found
		
		map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
		map.get(9);             // val9

		map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
		map.get(9); 
		
		
		// Date API
		Clock clock = Clock.systemDefaultZone();
		long millis = clock.millis();
		System.out.println(millis);
		Instant instant = clock.instant();
		System.out.println(instant);
		
		System.out.println(ZoneId.getAvailableZoneIds());

		ZoneId zone1 = ZoneId.of("Europe/Berlin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		System.out.println(zone1.getRules());
		System.out.println(zone2.getRules());
		
		LocalTime now1 = LocalTime.now(zone1);
		LocalTime now2 = LocalTime.now(zone2);

		System.out.println(now1.isBefore(now2));  // false

		long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
		long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

		System.out.println(hoursBetween);       // -3
		System.out.println(minutesBetween);     // -239
		
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		today = tomorrow.minusDays(1);

		LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
		DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
		System.out.println(dayOfWeek);    // Friday
		
		LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

		DayOfWeek day = sylvester.getDayOfWeek();
		System.out.println(day);      // WEDNESDAY

		Month month = sylvester.getMonth();
		System.out.println(month);          // DECEMBER

		long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
		System.out.println(minuteOfDay);    // 1439
	}
}

interface Formula {
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}

@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}

class Person {
    String firstName;
    String lastName;

    Person() {}

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
