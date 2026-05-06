package com.offmind.aiflappybird.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.offmind.aiflappybird.designsystem.theme.InkDark
import com.offmind.aiflappybird.designsystem.theme.SootLight

// Brass plate primary button with gradient and riveted look
@Composable
fun CogwingPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val shape = MaterialTheme.shapes.medium
    val contentColor = if (enabled) InkDark else InkDark.copy(alpha = 0.4f)
    val gradientColors = if (enabled) {
        listOf(BrassLight, Brass, BrassDark)
    } else {
        listOf(
            BrassLight.copy(alpha = 0.4f),
            Brass.copy(alpha = 0.4f),
            BrassDark.copy(alpha = 0.4f),
        )
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(Brush.verticalGradient(gradientColors))
            .border(BorderStroke(1.dp, BrassDark), shape)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(PaddingValues(horizontal = 24.dp, vertical = 12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = contentColor,
        )
    }
}

// Secondary outline button with brass border
@Composable
fun CogwingSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val shape = MaterialTheme.shapes.medium
    val alpha = if (enabled) 1f else 0.4f

    Box(
        modifier = modifier
            .clip(shape)
            .background(SootLight.copy(alpha = alpha * 0.3f))
            .border(BorderStroke(1.dp, Brass.copy(alpha = alpha)), shape)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(PaddingValues(horizontal = 24.dp, vertical = 12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = Brass.copy(alpha = alpha),
        )
    }
}

// Danger/ember button
@Composable
fun CogwingDangerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val shape = MaterialTheme.shapes.medium
    val emberColor = MaterialTheme.colorScheme.error
    val alpha = if (enabled) 1f else 0.4f

    Box(
        modifier = modifier
            .clip(shape)
            .background(SootLight.copy(alpha = alpha * 0.3f))
            .border(BorderStroke(1.dp, emberColor.copy(alpha = alpha)), shape)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(PaddingValues(horizontal = 24.dp, vertical = 12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = emberColor.copy(alpha = alpha),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingButtonPreview() {
    CogwingTheme(darkTheme = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            CogwingPrimaryButton(text = "Take Wing", onClick = {})
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingSecondaryButtonPreview() {
    CogwingTheme(darkTheme = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            CogwingSecondaryButton(text = "Logbook", onClick = {})
        }
    }
}
