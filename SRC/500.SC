;**
;**	Logics for room 500 -- Bamboo maze
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 8, 1989
;**


;**	The maze is shaped like this:
;**
;**			blockNorth	blockSouth	blockWest	blockEast
;**
;**	0		10111111		01001100		11010000		10100001		0
;**	8		01001100 	00010100 	11111001 	11110011 	8
;**	16		00010100		00111001		11100111		11001111		16
;**	24		00111001 	01001100 	10110011 	01100111 	24
;**	32		01001100		11010010		10010001		00100011		32
;**	40		11010010 	01101100 	10001101 	00011011 	40
;**	48		01101100		11000110		10010001		00100011		48
;**	56		11000110 	01000110 	11011000 	10110001 	56
;**	64		01000110		11110111		10001100		00011001		64
;**
;**			blockNorth	blockSouth	blockWest	blockEast


(script#				500)

(procedure
	RedrawMaze
	BitSet
)


(public
	rm500				0
	RedrawMaze		1
	BitSet			2
)


(define	vPslumping					501)
(define	vPdoggie						502)
(define	vPcrawling					503)
(define	vPdeadOfThirst				504)
(define	vPdeadBamboo				505)

;(define	sPbamboo						500)
(define	sPslumping					501)
(define	sPdoggie						502)
(define	sPcrawling					503)


(define	pBlockNorth					4)		;**	add to curPicNum for addToPic
(define	pBlockSouth					2)
(define	pBlockWest					1)
(define	pBlockEast					3)

(define	northOffset					 0)	;**		We only access the 4 arrays
(define	southOffset			(* 16 5))	;**	below from "blockNorth", adding
(define	westOffset			(* 16 10))	;**	these offsets to the lclRoom to
(define	eastOffset			(* 16 15))	;**	get correct word in the 4 arrays.


(enum												;**	RoomScript state values
	intro
	tellHerHowShe'sDoing

	startDying
	slowHerDown
	don'tFeelGood
	cleveland
	that'sAllFolks
)


(local
	[string  66] 
	[string2 22]
	blockNorth = [ %1011111101001100
						%0001010000111001
						%0100110011010010
						%0110110011000110
						%0100011011111111 ]

	blockSouth = [ %0100110000010100
						%0011100101001100
						%1101001001101100
						%1100011001000110
						%1111011111111111 ]

	blockWest = [ %1101000011111001
						%1110011110110011
						%1001000110001101
						%1001000111011000
						%1000110011111111 ]

	blockEast = [ %1010000111110011
						%1100111101100111
						%0010001100011011
						%0010001110110001
						%0001100111111111 ]

	pic = 500
	lclRoom
	roomCount
	lastEdge
)


(instance rm500 of Room
	(properties
		horizon		22
		picture		500
	)

	(method (init &tmp i)

		(for ((= i 500)) (< i 510) ((++ i))
			(Load PICTURE i)
		)

		(Load VIEW vPatti)
		(Load VIEW vPslumping)
		(Load VIEW vPdoggie)
		(Load VIEW vPcrawling)

		(if (ego has iBottle)
			(Load VIEW ((Inventory at iBottle) view))
		)

		(Load SOUND sPslumping)
		(Load SOUND sPdoggie)
		(Load SOUND sPcrawling)

		(super init)

		(music
	 		number		sPbamboo,
			loop			bgMusicLoops,
			play
		)

		(if (not egoIsPatti)
			(= egoIsPatti TRUE)
			(= normalEgoView vPatti)
		)

		(self
			setScript 		RoomScript
		)

		(if (== prevRoomNum 510)
			(ego posn 177 26)
			(= lclRoom 1)
		else
			(ego posn (Random 130 234) 188)			;**	bottom of pic.500
			(= lclRoom 68)
		)

		(RedrawMaze)
		(NormalEgo)
		(ego					
			baseSetter		SteadyBase,
			setCycle			SlowWalk,
			init
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		;**	Screen edges for edgeHit	(enum	1 NORTH EAST SOUTH WEST)

		(if (ego edgeHit)
			(= lastEdge (ego edgeHit))
			(ego
				edgeHit 				0 ,
				illegalBits			0,
			)
			(theGame setCursor waitCursor TRUE)
			(HandsOff)
			(++ roomCount)


			;**	Wait 3 seconds, maybe print a message or change music...
			(self changeState intro)

			(cond
				(( < roomCount 8)
					(ego
						view 			vPatti,
						moveSpeed	0,
					)
				)
				(( < roomCount 14)
					(ego
						view			vPslumping,
						moveSpeed	0,
					)
					(if (!= sPslumping (music number))
						(music fade)
					)
				)
				(( < roomCount 17)
					(ego
						view 			vPdoggie,
						moveSpeed	1,
					)
					(if (!= sPdoggie (music number))
						(music fade)
					)
				)
				(( < roomCount 18)
					(ego
						view 			vPcrawling,
						moveSpeed	2,
					)
					(if (!= sPcrawling (music number))
						(music fade)
					)
				)
				(else
					(ego
						view 			vPcrawling,
						moveSpeed	3,
					)
					(self changeState startDying)
				)
			)


			(switch lastEdge
				(NORTH
					(if (== lclRoom 1)
						(music fade)
						(if (not (TstFlg beenIn510))
							(theGame changeScore 100)
							(Print "YOU DID IT!")
							(Print "As you stumble out of the bamboo forest,
								you find yourself beside a beautiful stream.")
						)
						(curRoom newRoom 510)	;**	she made it across
						(return)
					else
						(-= lclRoom 8)
					)
				)

				(SOUTH
					(if (== lclRoom 68)
						(curRoom newRoom 245)	;**	she's heading back to town
						(return)
					else
						(+= lclRoom 8)
					)
				)

				(EAST
					(++ lclRoom)
				)

				(WEST
					(-- lclRoom)
				)
			)


			(if (== pic 505)
				(= pic 500)
				(switch lastEdge
					(NORTH					(ego posn (Random 130 234) 187))
					(SOUTH					(ego posn 177 26))
					(EAST						(ego posn	1 74))
					(else						(ego posn 317 74))
				)
			else
				(= pic 505)
				(switch lastEdge
					(NORTH					(ego posn (Random 80 163) 187))
					(SOUTH					(ego posn 188 26))
					(EAST						(ego posn	1 83))
					(else						(ego posn 314 128))
				)
			)

			(RedrawMaze)
			(RedrawCast)
			(ego	illegalBits	cLtWhite)
			(HandsOn)
			(theGame setCursor normalCursor (HaveMouse))
			(return)
		)

		(if (== (GameIsRestarting) RESTORING)
			(RedrawMaze)
			(RedrawCast)
		)
	)


	(method (handleEvent event &tmp i)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'get/bamboo')
				(Print "You try your best to pull the nearest bamboo stalk out of
					the ground, but you are unable to budge it.")
			)

			((Said 'climb/bamboo')
				(Print "It would never hold you.")
			)

			((Said 'hit/bamboo')
				(Print "You ARE frustrated, aren't you, Patti?")
			)

			((Said 'rise, (get, stand<up)')
				(Print "You simply can not.")
			)

			((or	(Said 'sip/water')
					(Said 'take/drink<one')
					(Said 'use, drink, empty/water, wine, bottle'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (ego has iBottle))
						(DontHave)
					)
					((== ((Inventory at iBottle) view) ivEmptyBottle)
						(Print "Either this wine is VERY dry, or
							your bottle is empty!"
							#icon ivEmptyBottle 0 0
						)
					)
					(else							;**	It must have water in it.
						(Ok)
						(theGame changeScore 20)
						(= roomCount 0)
						(music
	 						number		sPbamboo,
							loop			bgMusicLoops,
							play
						)
						(Print "You briefly consider a conservative rationing plan
							for your precious water.
							Oh, what the hell!
							Instead, you chug-a-lug the entire liter!
							Boy! 
							You didn't realize how thirsty you really were!"
							#icon ivWaterBottle 0 0
						)
						(Print "Blindly celebrating your thirst-crazed delirium, 
							you throw the now-empty wine bottle far out into the 
							bamboo thicket in protest of bottle-bill laws everywhere!")
						(PutInRoom iBottle)
						(NormalEgo)
						(ego					
							baseSetter		SteadyBase,
							setCycle			SlowWalk,
						)
						(self changeState intro)
					)
				)
			)

			((Said 'look>')
				(cond
					((Said '[/room]')
						(Print "You see lots of bamboo everywhere.")
						(Print "(Are you sure you know what you're doing here)" LOW)
					)

					((Said '/bamboo')
						(Print "You carefully examine the bamboo stalk closest to you.
							It looks exactly like every other bamboo stalk you've
							ever seen.")
						(Print (Format @string "Which is exactly %d." bambooCount) LOW)
						(++ bambooCount)
					)
				)
			)

;**				(debugging
;**					(cond
;**						((Said 'set/timer')
;**							(Print "Ok. You get 8 more rooms!")
;**							(-= roomCount 8)
;**						)
;**	
;**						((Said 'debug/maze')
;**							(Printf "lstEdge = %d\n
;**										lclRoom = %d\n
;**										picture = %d\n
;**										egoView = %d\n
;**										rmCount = %d"
;**										lastEdge lclRoom pic (ego view) roomCount)
;**						)
;**	
;**						((Said 'test/maze')
;**							(= i 0)
;**							(while ( < i 72)
;**								(Print
;**									(Format @string
;**										"			%s\n
;**							 			%s ____ %d ____ %s\n
;**													%s"
;**										(if (BitSet (+ northOffset i)) {BLOCKED} else {clear})
;**										(if (BitSet (+  westOffset i)) {BLOCKED} else {clear})
;**										i
;**										(if (BitSet (+  eastOffset i)) {BLOCKED} else {clear})
;**										(if (BitSet (+ southOffset i)) {BLOCKED} else {clear})
;**										(++ i)
;**									)
;**								#mode		teJustCenter
;**								)
;**							)
;**						)
;**					)
;**				)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(delay 4)
			)

			(tellHerHowShe'sDoing
				(cond
					((and	(>= roomCount 8)
							(<= roomCount 13)
							(!= sPslumping (music number)))
						(music
							number 		sPslumping ,
							loop			bgMusicLoops,
							play,
						)
					)
					((and	(>= roomCount 14)
							(<= roomCount 16)
							(!= sPdoggie (music number)))
						(music
							number 		sPdoggie ,
							loop			bgMusicLoops,
							play,
						)
					)
					((and (<= roomCount 18)
							(>= roomCount 17)
							(!= sPcrawling (music number)))
						(music
							number 		sPcrawling ,
							loop			bgMusicLoops,
							play,
						)
					)
				)

				(cond
					((== roomCount 4)
						(Print "\"Boy, could I use a good belt about now!\"
							you think to yourself.")
					)
					((== roomCount 8)
						(Print "You think to yourself,
							\"I'm sweating worse than a jazz clarinetist
							in a cowboy bar!\"")
					)
					((== roomCount 12)
						(Print "\"Like, there wouldn't be a nice little
							cocktail lounge hidden out in this gawdfersakin'
							mess, would there?\"")
					)
					((== roomCount 16)
						(Print "\"Why is everything here shaped like a swizzle
							stick?\"")
						(Print "\"Why am I crawling around like this?\"")
						(Print "\"Why am I talking to myself?\"")
						(Print "\"I'M DELIRIOUS!!!\"")
					)
				)
			)


			;**	This is what happens when she gets real slow...

			(startDying
				(delay 3)
			)

			(slowHerDown
				(Print "You are feeling VERY tired and thirsty.")
				(delay 3)
			)

			(don'tFeelGood
				(Print "You have almost no energy left.")
				(delay 3)
			)

			(cleveland
				(HandsOff)
				(Print "As you take leave of your withered, dehydrated body
					you wonder, \"Is this what it's like to play Cleveland?\"")
				(ego
					illegalBits		FALSE ,
					setMotion		FALSE ,
					view				vPdeadOfThirst ,
					cel				0 ,
					cycleSpeed		2 ,
					setCycle			EndLoop self,
				)
			)

			(that'sAllFolks
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vPdeadBamboo,
					register 	(Format @string
										"Patti, you really must start packing
										more sensibly when you take these 
										little outings of yours!"),
					next			(Format @string2 "Bamboo-zled"),
				)
			)
		)
	)
)

(procedure (RedrawMaze &tmp i)
	(curRoom drawPic pic)
	(if (BitSet (+ northOffset lclRoom)) (curRoom overlay (+ pic pBlockNorth)))
	(if (BitSet (+ southOffset lclRoom)) (curRoom overlay (+ pic pBlockSouth)))
	(if (BitSet (+  eastOffset lclRoom)) (curRoom overlay (+ pic pBlockEast)))
	(if (BitSet (+  westOffset lclRoom)) (curRoom overlay (+ pic pBlockWest)))
)

(procedure (BitSet index)
	(return (if (& [blockNorth (/ index 16)] (>> $8000 (mod index 16))) TRUE else FALSE))
)

(instance SteadyBase of Code
	(method (doit)
		(ego brBottom		(+ (ego y) 1 ))
		(ego brTop			(- (ego brBottom) 2))
		(ego brLeft			(- (ego x) 10))
		(ego brRight		(+ (ego x) 10))
	)
)

(class SlowWalk of Forward
	(method (doit)
		(if (or	(!= (client x) (client xLast))
					(!= (client y) (client yLast)))
			(super doit)
		)
	)
)

