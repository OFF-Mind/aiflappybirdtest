package com.offmind.aiflappybird.designsystem.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.offmind.aiflappybird.designsystem.theme.Brass
import com.offmind.aiflappybird.designsystem.theme.BrassDark
import com.offmind.aiflappybird.designsystem.theme.BrassLight
import com.offmind.aiflappybird.designsystem.theme.CogwingTheme
import com.offmind.aiflappybird.designsystem.theme.Soot
import com.offmind.aiflappybird.designsystem.theme.SootLight
import com.offmind.aiflappybird.designsystem.theme.Verdigris

private val TrackWidth = 52.dp
private val TrackHeight = 26.dp
private val KnobSize = 20.dp
private val KnobPadding = 3.dp

// Brass-knob toggle on engraved track
@Composable
fun CogwingSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    val knobOffset by animateDpAsState(
        targetValue = if (checked) TrackWidth - KnobSize - KnobPadding else KnobPadding,
        animationSpec = tween(durationMillis = 140),
        label = "knobOffset",
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(TrackWidth)
                .height(TrackHeight)
                .clip(RoundedCornerShape(TrackHeight / 2))
                .background(if (checked) Verdigris.copy(alpha = 0.3f) else SootLight)
                .border(1.dp, if (checked) Verdigris else Brass.copy(alpha = 0.4f), RoundedCornerShape(TrackHeight / 2))
                .clickable { onCheckedChange(!checked) },
        ) {
            // Knob
            Box(
                modifier = Modifier
                    .offset(x = knobOffset, y = KnobPadding)
                    .size(KnobSize)
                    .clip(CircleShape)
                    .background(
                        if (checked) {
                            Brush.verticalGradient(listOf(BrassLight, Brass, BrassDark))
                        } else {
                            Brush.verticalGradient(
                                listOf(
                                    Soot,
                                    SootLight,
                                ),
                            )
                        },
                    )
                    .border(1.dp, if (checked) BrassDark else Brass.copy(alpha = 0.3f), CircleShape),
            )
        }

        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingSwitchPreview() {
    CogwingTheme(darkTheme = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            CogwingSwitch(checked = true, onCheckedChange = {}, label = "Sound")
        }
    }
}
