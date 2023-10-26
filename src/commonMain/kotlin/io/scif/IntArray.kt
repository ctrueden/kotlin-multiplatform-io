import net.imglib2.img.basictypeaccess.DataAccess
import net.imglib2.img.basictypeaccess.IntAccess

/**
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
//class IntArray : AbstractIntArray<IntArray?> {
//    protected var data: kotlin.IntArray
//
//    constructor(numEntities: Int) : super(numEntities)
//    constructor(data: kotlin.IntArray?) : super(data)
//
//    fun createArray(numEntities: Int): IntArray {
//        return IntArray(numEntities)
//    }
//}


/**
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
abstract class AbstractIntArray<A : AbstractIntArray<A>?> : IntAccess, ArrayDataAccess<A> {
//    var currentStorageArray: kotlin.IntArray
//        protected set

//    constructor(numEntities: Int) {
//        currentStorageArray = kotlin.IntArray(numEntities)
//    }
//
//    constructor(data: kotlin.IntArray) {
//        currentStorageArray = data
//    }
//
//    override fun getValue(index: Int): Int {
//        return currentStorageArray[index]
//    }
//
//    override fun setValue(index: Int, value: Int) {
//        currentStorageArray[index] = value
//    }
//
//    val arrayLength: Int
//        get() = currentStorageArray.size
}

/**
 * Trivial interface for primitive array based data access implementations
 * that can replicate themselves and return the underlying array.
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
//@kotlinx.serialization.Serializable
interface ArrayDataAccess<A> : DataAccess {
    fun createArray(numEntities: Int): A
    val currentStorageArray: Any?
    val arrayLength: Int
}

