@file:JvmName("PropertiesHelper")

package com.zybf.math.correct.common.utils

import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat

/**
 * PropertiesNoGetter
 * @author junpu
 * @date 2020/8/27
 */
object PropertiesNoGetter {
    const val NO_GETTER: String = "Property does not have a getter"
    fun noGetter(): Nothing = throw AthenaException("Property does not have a getter")
}

open class AthenaException(message: String = "") : RuntimeException(message)

var View.backgroundColor: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setBackgroundColor(v)

var View.backgroundResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setBackgroundResource(v)

var ImageView.imageResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setImageResource(v)

var ImageView.imageURI: android.net.Uri?
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setImageURI(v)

var ImageView.imageBitmap: android.graphics.Bitmap?
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setImageBitmap(v)

var TextView.textColor: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setTextColor(v)

var TextView.hintTextColor: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setHintTextColor(v)

var TextView.linkTextColor: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setLinkTextColor(v)

var TextView.hintResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setHint(v)

var TextView.textResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setText(v)

var RelativeLayout.horizontalGravity: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setHorizontalGravity(v)

var RelativeLayout.verticalGravity: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setVerticalGravity(v)

var LinearLayout.horizontalGravity: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setHorizontalGravity(v)

var LinearLayout.verticalGravity: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setVerticalGravity(v)

var AbsListView.selectorResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setSelector(v)

var CheckedTextView.checkMarkDrawableResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setCheckMarkDrawable(v)

var CompoundButton.buttonDrawableResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setButtonDrawable(v)

var TabWidget.leftStripDrawableResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setLeftStripDrawable(v)

var TabWidget.rightStripDrawableResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setRightStripDrawable(v)

var Toolbar.logoResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setLogo(v)

var Toolbar.logoDescriptionResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setLogoDescription(v)

var Toolbar.navigationContentDescriptionResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setNavigationContentDescription(v)

var Toolbar.navigationIconResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setNavigationIcon(v)

var Toolbar.subtitleResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setSubtitle(v)

var Toolbar.titleResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(v) = setTitle(v)

var View.backgroundColorResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(colorId) = setBackgroundColor(ContextCompat.getColor(context, colorId))

var View.leftPadding: Int
    inline get() = paddingLeft
    set(value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

var View.topPadding: Int
    inline get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

var View.rightPadding: Int
    inline get() = paddingRight
    set(value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

var View.bottomPadding: Int
    inline get() = paddingBottom
    set(value) = setPadding(paddingLeft, paddingTop, paddingRight, value)

var View.horizontalPadding: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(value) = setPadding(value, paddingTop, value, paddingBottom)

var View.verticalPadding: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(value) = setPadding(paddingLeft, value, paddingRight, value)

var View.padding: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    inline set(value) = setPadding(value, value, value, value)

var TextView.ems: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    inline set(value) = setEms(value)

inline var TextView.isSelectable: Boolean
    get() = isTextSelectable
    set(value) = setTextIsSelectable(value)

var TextView.textAppearance: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(value) = TextViewCompat.setTextAppearance(this, value)

var TextView.textSizeDimen: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(value) = setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(value))

var TextView.textColorResource: Int
    @Deprecated(
        PropertiesNoGetter.NO_GETTER,
        level = DeprecationLevel.ERROR
    ) get() = PropertiesNoGetter.noGetter()
    set(colorId) = setTextColor(ContextCompat.getColor(context, colorId))

var ImageView.image: Drawable?
    inline get() = drawable
    inline set(value) = setImageDrawable(value)