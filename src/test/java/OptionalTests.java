import org.junit.Test;

import java.util.Optional;
import java.util.function.Block;

import static junit.framework.Assert.*;

public class OptionalTests {

    @Test
    public void optionalsTellYouIfTheyHaveValues(){
         Optional<Integer> one = Optional.of(1);
         Optional<Integer> notOne = Optional.empty();

         assertTrue(one.isPresent());
         assertFalse(notOne.isPresent());
         assertEquals((Integer) 1, one.get());
     }

    @Test(expected = NullPointerException.class)
    public void optionalOfIsNotSoOptional(){
        Optional.of((Integer)null);
    }

    @Test
    public void canAppBlockToItem(){
        Optional<Foo> aFoo = Optional.of(new Foo("foo"));
        Optional<Foo> notAFoo = Optional.empty();

        Block<Foo> doThis = (x)->{x.value = "bar";};

        aFoo.ifPresent(doThis);
        notAFoo.ifPresent(doThis);

        assertEquals("bar", aFoo.get().value);
    }

    @Test
    public void orElseDo(){
        Optional<Foo> aFoo = Optional.of(new Foo("foo"));
        Optional<Foo> notAFoo = Optional.empty();

        assertEquals("foo", aFoo.orElse(new Foo("baz")).value);
        assertEquals("baz", notAFoo.orElse(new Foo("baz")).value);
    }

    @Test(expected = YesThisShouldBeThrown.class)
    public void orElseThrow(){
        Optional<Foo> aFoo = Optional.of(new Foo("foo"));
        Optional<Foo> notAFoo = Optional.empty();

        aFoo.orElseThrow(ShouldNotSeeThisException.class);
        notAFoo.orElseThrow(YesThisShouldBeThrown.class);
    }

    public static class ShouldNotSeeThisException extends RuntimeException {}
    public static class YesThisShouldBeThrown extends RuntimeException {}

}
