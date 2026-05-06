package com.offmind.aiflappybird.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import com.offmind.aiflappybird.designsystem.theme.ParchmentLight
import com.offmind.aiflappybird.designsystem.theme.Soot
import com.offmind.aiflappybird.designsystem.theme.SootLight

@Composable
fun CogwingSegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerShape = MaterialTheme.shapes.small

    Row(
        modifier = modifier
            .clip(containerShape)
            .background(SootLight)
            .border(1.dp, Brass.copy(alpha = 0.5f), containerShape),
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (isSelected) {
                            Modifier
                                .background(Brush.verticalGradient(listOf(BrassLight, Brass, BrassDark)))
                        } else {
                            Modifier.background(Soot.copy(alpha = 0f))
                        },
                    )
                    .clickable { onOptionSelected(index) }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = option.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) InkDark else ParchmentLight.copy(alpha = 0.6f),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF14100C)
@Composable
private fun CogwingSegmentedControlPreview() {
    CogwingTheme(darkTheme = true) {
        Box(modifier = Modifier.padding(16.dp)) {
            CogwingSegmentedControl(
                options = listOf("Daily", "Weekly", "All Time"),
                selectedIndex = 1,
                onOptionSelected = {},
            )
        }
    }
}
