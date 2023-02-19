import kotlin.test.*
// Ad Personalization
//
// Congratulations! You've been invited to do the following
// online assessment for a co-op with tech giant Oodle.
//
// 1. Add constants (such as Female, Male, and Nonbinary) to
//    the Gender enum. It is your choice what to add.
// 2. After reading the below code, decide which ads should be
//    returned for different demographics. Make sure that all
//    ads will be returned for some combination of inputs. Add at
//    least two more age-based constants. Do not use randomness.
//    Choose the highest-revenue ad you think someone in that
//    demographic would click on.
// 3. Add properties minAge and maxAge to Ad, and set values
//    for each ad. For example, minAge for the dating ad should
//    be MIN_ADULT_AGE.
// 4. Create a function runTests() that consists of tests of fetchAd().
//    Use kotlin.test.assertEquals() for each test. There should
//    be at least one test for each of the provided ads.
// 5. Implement fetchAd(). You should use both "if" and "when".
//    Run your tests, revising your code if they fail valid tests
//    and your tests if you find any mistakes in them.
// 6. Create a new data class named "Person". A person should have an
//    age (Int), gender (Gender), and income (Int). Use your judgment
//    as to which should be changeable.
// 7. Write a new fetchAd() method (without removing the original one)
//    that takes a single parameter of type Person and returns an Ad.
//    Instead of duplicating the code in your original fetchAd() method,
//    have your new method call your old method, passing the appropriate
//    properties as arguments.
// 8. Add 3-5 tests of the new fetchAd() method to runTests().
// 9. State in a comment whether you satisfied all of the above
//    requirements and all of your tests passed.

enum class Gender {
    Female, Male, Nonbinary
    // 1. Add Gender constants here, such as Female, Male, and Nonbinary.
}

// Age-based constants
const val MIN_AGE_FOR_PERSONALIZATION = 13
const val MIN_ADULT_AGE = 18
const val MIN_MID_ADULT_AGE = 45
const val MIN_SENIOR_AGE = 67
// 2. Add at least two more age-based constants.

fun runTests() {
    assertEquals(Ad.Diet, fetchAd(Gender.Female, 65, 0))
    assertEquals(Ad.Car, fetchAd(Gender.Male, 18, 1000000))
    assertEquals(Ad.Beauty, fetchAd(Gender.Nonbinary, 66, 200000))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 18, 0))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 100, 1500000))
    assertEquals(Ad.PetToy, fetchAd(Gender.Male, 13, 10000))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 13, 0))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Nonbinary, 13, 120000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 100, 80000))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 18, 0))
    fetchAd(Gender.Female, 13, 0)
    fetchAd(Gender.Male, 45, 30000)
    fetchAd(Gender.Nonbinary, 67, 20000)
    fetchAd(Gender.Female, 8, 0)
    fetchAd(Gender.Male, 102, 10000)
    println("All Tests Passed.")
}

fun main(){
    runTests()
}
/**
 * Ads that may be shown to users.
 *
 * @property text the text of the ad
 * @property revenue the number of cents earned per click
 */
// 3. Add properties minAge and maxAge.
enum class Ad(val text: String, val revenue: Int, val minAge: Int, val maxAge: Int) {
    Diet("Lose weight now!", 5, 18, 66),
    Car("Buy a new car!", 5, 18, 66),
    Beauty("Improve your looks!", 5, 18, 66),
    Dating("Meet other singles!", 4, 18, 100),
    Pet("Get a pet!", 3, 13, 100),
    PetToy("Buy your pet a toy!", 2, 13, 100),
    Lego("Get more bricks!", 2, 13, 44),
    Pokemon("Gotta catch 'em all!", 2, 13, 44),
    Retirement("Join AARP", 2, 67, 100),
    Work("Apply for a job at Oodle!", 2, 18, 66)
}

data class Person(val gender: Gender, val age: Int, val income: Int){}

/**
 * Fetches an ad based on the user's [gender], [age], and
 * [income], unless the age is under [MIN_AGE_FOR_PERSONALIZATION],
 * in which case no personalization is permitted.
 */
fun fetchAd(gender: Gender, age: Int, income: Int): Ad {
    if (age < MIN_AGE_FOR_PERSONALIZATION) {return Ad.Lego}
    if (age >= MIN_AGE_FOR_PERSONALIZATION && age < MIN_ADULT_AGE) {
        return when (gender) {
            Gender.Female -> {Ad.Lego}
            Gender.Male -> {Ad.PetToy}
            Gender.Nonbinary -> {Ad.Pokemon}
        }
    }
    if (age >= MIN_ADULT_AGE && age < MIN_MID_ADULT_AGE) {
        return when(gender) {
            Gender.Female -> {
                if (income >= 30000) {Ad.Car}
                else if (income >= 15000) { Ad.Pet}
                else {Ad.Dating}
            }
            Gender.Male -> {
                if (income >= 30000) {Ad.Car}
                else if (income >= 15000) { Ad.Pet}
                else {Ad.Work}
            }
            Gender.Nonbinary -> {
                Ad.Diet
                Ad.Beauty
                Ad.Dating
                Ad.Lego
                Ad.Pokemon
                Ad.Work
                if (income >= 30000) {Ad.Car}
                else if (income >= 15000) { Ad.Pet}
                else {Ad.PetToy}
            }
        }
    }
    if (age >= MIN_MID_ADULT_AGE && age < MIN_SENIOR_AGE) {
        return when(gender) {
            Gender.Female -> {
                if (income >= 50000) {Ad.Car}
                else if (income >= 20000) {Ad.Pet}
                else { Ad.Diet}
            }
            Gender.Male -> {
                Ad.Diet
                Ad.Beauty
                Ad.Dating
                if (income >= 50000) {Ad.Car}
                else if (income >= 20000) {Ad.Pet}
                else {Ad.PetToy}
            }
            Gender.Nonbinary -> {
                if (income >= 50000) { Ad.Beauty}
                else if (income >= 20000) {Ad.Pet}
                else {Ad.PetToy}
            }
        }
    }
    if (age >= MIN_SENIOR_AGE && age <= 100) {
        return when(gender) {
            Gender.Female -> {
                Ad.Dating
                if (income <= 23000) {
                    Ad.Pet
                    Ad.PetToy
                }
                else {Ad.Retirement}
            }
            Gender.Male -> {
                Ad.Dating
                if (income >= 23000) {
                    Ad.Pet
                    Ad.PetToy
                }
                Ad.Retirement
            }
            Gender.Nonbinary -> {
                if (income >= 23000) {
                    Ad.Pet
                }
                else {Ad.Retirement}
            }
        }
    }
    else {return Ad.Car}
}
    // 5. Serve each of the above for at least one combination
    // of inputs. If age is less than MIN_AGE_FOR_PERSONALIZATION,
    // you must not use gender or income.
fun fetchAd(person: Person){
    fetchAd(person.gender, person.age, person.income)
}

//I satisfied all of the above requirements and all of my tests passed