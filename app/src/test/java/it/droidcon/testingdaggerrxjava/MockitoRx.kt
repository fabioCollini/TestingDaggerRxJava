package it.droidcon.testingdaggerrxjava

import com.nhaarman.mockito_kotlin.given
import io.reactivex.Single
import org.mockito.BDDMockito

infix fun <T> BDDMockito.BDDMyOngoingStubbing<Single<T>>.willReturnSingle(value: () -> T): BDDMockito.BDDMyOngoingStubbing<Single<T>> =
        willReturn(Single.fromCallable(value))

infix fun <T> BDDMockito.BDDMyOngoingStubbing<Single<T>>.willReturnJust(value: T): BDDMockito.BDDMyOngoingStubbing<Single<T>> =
        willReturn(Single.just(value))

infix fun <T> BDDMockito.BDDMyOngoingStubbing<T?>.andThen(value: T): BDDMockito.BDDMyOngoingStubbing<T?> =
        willReturn(value)

infix fun <T> BDDMockito.BDDMyOngoingStubbing<Single<T>?>.andThenJust(value: T): BDDMockito.BDDMyOngoingStubbing<Single<T>?> =
        willReturn(Single.just(value))

infix fun <T> Single<T>?.willReturnJust(value: T): BDDMockito.BDDMyOngoingStubbing<Single<T>?> =
        given(this).willReturn(Single.just(value))

infix fun <T> Single<T>?.willThrow(value: Throwable): BDDMockito.BDDMyOngoingStubbing<Single<T>?> =
        given(this).willReturn(Single.error(value))

infix fun <T> T?.willReturn(value: T): BDDMockito.BDDMyOngoingStubbing<T?> =
        given(this).willReturn(value)
