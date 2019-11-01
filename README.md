# Hex

Janky version of Hex in Java.

Hex ~obviously means many things~ is a 2-player board game invented by mathematicians Piet Hein and John Nash (somehow independently). Normally it is played on a hexagonal board; however, it is well known to have an equivalent form on a square grid with diagonals drawn (which is easier to implement, so that's what I did). There are two players: red and blue. There is a pair of red edges and a pair of blue edges. Red and blue take turns placing stones of their color down on the board. The player that managed to connect their edges with stones of their corresponding color wins.

As an added rule, because the first player would otherwise have an overwhelming advantage, for the first move only, the second player can skip their move and instead "take" the first player's move. The players then swap turns. The original first player would get to go again, and would be referred to as the second player.

This game is mathematical interesting for many reasons. For instance, an interesting theorem to prove is that it is guaranteed someone will win this game; there can't be some weird tying configuration where neither red nor blue have paths connecting their edges. It also gets used to test out computer learning systems.
