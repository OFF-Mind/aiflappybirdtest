package com.offmind.aiflappybird.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.offmind.aiflappybird.designsystem.R
import com.offmind.aiflappybird.designsystem.theme.Brass
import com.offmind.aiflappybird.designsystem.theme.BrassDark
import com.offmind.aiflappybird.designsystem.theme.BrassLight
import com.offmind.aiflappybird.designsystem.theme.CogwingTheme
import com.offmind.aiflappybird.designsystem.theme.InkDark
import com.offmind.aiflappybird.designsystem.theme.Soot

@Composable
fun CogwingIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    style: IconButtonStyle = IconButtonStyle.Ghost,
) {
    val shape = MaterialTheme.shapes.small
    val styledModifier = when (style) {
        IconButtonStyle.Brass -> modifier
            .clip(shape)
            .background(Brush.verticalGradient(listOf(BrassLight, Brass, BrassDark)))
            .border(BorderStroke(1.dp, BrassDark), shape)
            .clickable(onClick = onClick)
            .padding(8.dp)

        IconButtonStyle.Ghost -> modifier
            .clip(shape)
            .background(Soot.copy(alpha = 0.4f))
            .border(BorderStroke(1.dp, Brass.copy(alpha = 0.5f)), shape)
            .clickable(onClick = onClick)
            .padding(8.dp)
    }

    Box(
        modifier = styledModifier,
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = if (style == IconButtonStyle.Brass) InkDark else Brass,
            modifier = Modifier.size(iconSize),
        )
    }
}

enum class IconButtonStyle { Brass, Ghost }

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingIconButtonPreview() {
    CogwingTheme(darkTheme = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            CogwingIconButton(
                iconRes = R.drawable.ic_settings,
                contentDescription = "Settings",
                onClick = {},
                style = IconButtonStyle.Ghost,
            )
        }
    }
}
