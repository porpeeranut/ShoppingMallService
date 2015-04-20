package com.dmbteam.catalogapp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.dmbteam.catalogapp.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * The Class RippleView.
 */
@SuppressLint("ClickableViewAccessibility")
public class RippleView extends Button {

	/** The Down x. */
	private float mDownX;
	
	/** The Down y. */
	private float mDownY;
	
	/** The Alpha factor. */
	private float mAlphaFactor;
	
	/** The Density. */
	private float mDensity;
	
	/** The Radius. */
	private float mRadius;
	
	/** The Max radius. */
	private float mMaxRadius;

	/** The Ripple color. */
	private int mRippleColor;
	
	/** The Is animating. */
	private boolean mIsAnimating = false;
	
	/** The Hover. */
	private boolean mHover = true;

	/** The Radial gradient. */
	private RadialGradient mRadialGradient;
	
	/** The Paint. */
	private Paint mPaint;
	
	/** The Radius animator. */
	private ObjectAnimator mRadiusAnimator;

	/**
	 * Dp.
	 *
	 * @param dp the dp
	 * @return the int
	 */
	private int dp(int dp) {
		return (int) (dp * mDensity + 0.5f);
	}

	/**
	 * Instantiates a new ripple view.
	 *
	 * @param context the context
	 */
	public RippleView(Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new ripple view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public RippleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Instantiates a new ripple view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public RippleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RippleView);
		mRippleColor = a.getColor(R.styleable.RippleView_rippleColor,
				mRippleColor);
		mAlphaFactor = a.getFloat(R.styleable.RippleView_alphaFactor,
				mAlphaFactor);
		mHover = a.getBoolean(R.styleable.RippleView_hover, mHover);
		a.recycle();
	}

	/**
	 * Inits the.
	 */
	public void init() {
		mDensity = getContext().getResources().getDisplayMetrics().density;

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setAlpha(100);
		setRippleColor(Color.BLACK, 0.2f);

	}

	/**
	 * Sets the ripple color.
	 *
	 * @param rippleColor the ripple color
	 * @param alphaFactor the alpha factor
	 */
	public void setRippleColor(int rippleColor, float alphaFactor) {
		mRippleColor = rippleColor;
		mAlphaFactor = alphaFactor;
	}

	/**
	 * Sets the hover.
	 *
	 * @param enabled the new hover
	 */
	public void setHover(boolean enabled) {
		mHover = enabled;
	}

	/* (non-Javadoc)
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mMaxRadius = (float) Math.sqrt(w * w + h * h);
	}

	/** The Animation is cancel. */
	private boolean mAnimationIsCancel;
	
	/** The Rect. */
	private Rect mRect;

	/* (non-Javadoc)
	 * @see android.widget.TextView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		Log.d("TouchEvent", String.valueOf(event.getActionMasked()));
		Log.d("mIsAnimating", String.valueOf(mIsAnimating));
		Log.d("mAnimationIsCancel", String.valueOf(mAnimationIsCancel));
		boolean superResult = super.onTouchEvent(event);
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN
				&& this.isEnabled() && mHover) {
			mRect = new Rect(getLeft(), getTop(), getRight(), getBottom());
			mAnimationIsCancel = false;
			mDownX = event.getX();
			mDownY = event.getY();

			mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", 0, dp(50))
					.setDuration(400);
			mRadiusAnimator
					.setInterpolator(new AccelerateDecelerateInterpolator());
			mRadiusAnimator.start();
			if (!superResult) {
				return true;
			}
		} else if (event.getActionMasked() == MotionEvent.ACTION_MOVE
				&& this.isEnabled() && mHover) {
			mDownX = event.getX();
			mDownY = event.getY();

			// Cancel the ripple animation when moved outside
			if (mAnimationIsCancel = !mRect.contains(
					getLeft() + (int) event.getX(),
					getTop() + (int) event.getY())) {
				setRadius(0);
			} else {
				setRadius(dp(50));
			}
			if (!superResult) {
				return true;
			}
		} else if (event.getActionMasked() == MotionEvent.ACTION_UP
				&& !mAnimationIsCancel && this.isEnabled()) {
			mDownX = event.getX();
			mDownY = event.getY();

			final float tempRadius = (float) Math.sqrt(mDownX * mDownX + mDownY
					* mDownY);
			float targetRadius = Math.max(tempRadius, mMaxRadius);

			if (mIsAnimating) {
				mRadiusAnimator.cancel();
			}
			mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", dp(50),
					targetRadius);
			mRadiusAnimator.setDuration(500);
			mRadiusAnimator
					.setInterpolator(new AccelerateDecelerateInterpolator());
			mRadiusAnimator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animator) {
					mIsAnimating = true;
				}

				@Override
				public void onAnimationEnd(Animator animator) {
					setRadius(0);
					ViewHelper.setAlpha(RippleView.this, 1);
					mIsAnimating = false;
				}

				@Override
				public void onAnimationCancel(Animator animator) {

				}

				@Override
				public void onAnimationRepeat(Animator animator) {

				}
			});
			mRadiusAnimator.start();
			if (!superResult) {
				return true;
			}
		}
		return superResult;
	}

	/**
	 * Adjust alpha.
	 *
	 * @param color the color
	 * @param factor the factor
	 * @return the int
	 */
	public int adjustAlpha(int color, float factor) {
		int alpha = Math.round(Color.alpha(color) * factor);
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		return Color.argb(alpha, red, green, blue);
	}

	/**
	 * Sets the radius.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(final float radius) {
		mRadius = radius;
		if (mRadius > 0) {
			mRadialGradient = new RadialGradient(mDownX, mDownY, mRadius,
					adjustAlpha(mRippleColor, mAlphaFactor), mRippleColor,
					Shader.TileMode.MIRROR);
			mPaint.setShader(mRadialGradient);
		}
		invalidate();
	}

	/** The Path. */
	private Path mPath = new Path();

	/* (non-Javadoc)
	 * @see android.widget.TextView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		if (isInEditMode()) {
			return;
		}

		canvas.save(Canvas.CLIP_SAVE_FLAG);

		mPath.reset();
		mPath.addCircle(mDownX, mDownY, mRadius, Path.Direction.CW);

		canvas.clipPath(mPath);
		canvas.restore();

		canvas.drawCircle(mDownX, mDownY, mRadius, mPaint);
	}

}
