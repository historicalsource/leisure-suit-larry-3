;**
;**	Logics for room 530 -- Marijuana ledge
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 28, 1989
;**


(script#				530)

(public
	rm530				0
)


(define	vRopeOfDope					530)
		(define	ropePosn 	227 72)
	(define	lRopeTightening 	0)
	(define	lRopeFalling	 	1)

(define	vPmarijuana 				531)
	(define	lBendingOver		0)
	(define	lPickingDope		1)
	(define	lMakingJoint		2)
	(define	lGetRocks			3)
	(define	lSparking			4)
	(define	lPuffing				5)
	(define	lDeepInhale			6)
	(define	lSmiling				7)
	(define	lKneeling			8)

(define	vPgettingNuts				532)
		(define	treePosn		128 132)
		(define	treeTopPosn	128  47)
	(define	lClimbingTree 		0)
	(define	lGettingNutsL 		1)
	(define	lGettingNutsR 		2)
	(define	lTits					3)

(define	vPstoned						533)
	(define	lReclining			0)
	(define	lFloating			1)
	(define	lWearingOff			2)

(define	vPlasso						534)
		(define	throwPosn	141 129)
		(define	throwX		141)
		(define	throwY		129)
		(define	holdRopeX 	142)
		(define	holdRopeY	128)
	(define	lMissing				0)
	(define	lSwinging			1)
	(define	lMadeIt				2)
	(define	lTying				3)
	(define	lTearingDress		4)
	(define	lHarness				5)
	(define	lMountingRope		6)
		(define	mountPosn	149 107)

(define	vPdeadOfThirst				537)


;(define	vPsmall						802)

;(define	vPfallingIn					804)
	(define	lPfallingIn			0)
	(define	lFloor2standingEL	1)

;(define	vPfalling					813)
;	(define	lFalling				0)
;	(define	lFallingSmall		1)
;(define	vPdeadFalling				814)


;(define	sFalling						004)
;(define	sLasso						530)
(define	sHighOnDrugs				531)
;(define	sHitBottom					012)


(define	nearMarijuana			(& (ego onControl) cCyan))
(define	nearRightTree			(& (ego onControl) cRed))
(define	nearWrongTree			(& (ego onControl) cMagenta))
(define	nearCliffEdge			(& (ego onControl) cGreen))


(enum												;**	RoomScript state values
	intro
	fromRm525
	hitDirt
	youshake
	itsLarry
	getUp
	backToNormal
	getDope
	pickDope
	smallDelay
	stanDup
	pickDopeDone
	dyingOfThirst
	hitTheDeck
	deadOfThirst
	moveToCenterStage
	onYourKneesBitch
	takeAwayPoints
	smokeDope
	getRocks
	sparking
	sparkDelay
	cyclingForward
	inhaleIt
	getHappy
	buzz
	liftOff
	clearedGround
	whoa
	feelsLikeFloating
	clearOfCliff
	farOut
	farOut2
	wearingOff
	roadRunnerTake
	climbTree
	StopCycling
	grabYourNuts
	verySmallDelay
	pickNuts2
	sitAround
	stillUpTree
	goDown
	onBottom
	makeArope
	ropeMaid
	throwRope
	windUp
	tryAndMiss
	delayAfterMiss
	lastTry
	ropeOnRock
	waitingToTie
	tieRopeToTree
	tinyDelay
	doneTying
	makeHarness
	allDone
	dropRope
	youreDead
	mountRope
	putOnHarness
	mountUp
	gotoNextRoom
	changeRooms
	startFalling
	startThefall
	fallDone
	demo1
	demo2
	demo3
	demo4
)


(local
	attempts
	onOtherSide
	moveCounter
	potState
		(enum 									;**	potState
			HERE
			PICKED
			ROPE
			THROWN
			TIED
				530
			SMOKED
			FALLEN
		)
	[string 	44] 
	[string2 22]
)


(enum 530										;**	local egoState values
	FALLING_IN
	THROWING_ROPE
	ON_ROPE
	CLIMBING_TREE
	SMOKING_DOPE
	CHEWING_DOPE
	AT_TREE_TOP
)


(synonyms
	(grass dope )
	(tree trunk )
)

(instance rm530 of Room
	(properties
		picture		530
		horizon		1
	)

	(method (init)
		(Load VIEW vPfalling)
		(Load SOUND sFalling)
		(Load SCRIPT JUMP)

		(cls)										;**	if 525 left a print window

		(super 				init)

		(self
			setScript 		RoomScript
		)

		(aRope				init)

		(if (!= prevRoomNum 525)
			(music
				number			sJungleRegion,
			 	loop				bgMusicLoops,
				play,
			)
		)
		(cond
			((== prevRoomNum 535)			;**	Tiny Patti crossing the rope.
				(= egoIsPatti TRUE)			;**	Just in case
				(= onOtherSide TRUE)
				(= potState FALLEN)
				(= normalEgoView vPsmall)
				(NormalEgo facingDown)
				(ego
					setStep	 		2 1,
					posn 				215 75,
					init,
				)
				(aRope
					posn				ropePosn,
					setCycle			EndLoop RopeScript,
				)
			)

			((== prevRoomNum 540)			;**	Feral pig suite
				(= egoIsPatti TRUE)			;**	Just in case
				(= onOtherSide TRUE)
				(= potState FALLEN)
				(= normalEgoView vPsmall)
				(NormalEgo facingDown)
				(ego
					setStep	 		2 1,
					posn				31 78,
					init,
				)
				(aRope
					posn				ropePosn,
					setCel			LastCel,
					stopUpd,
				)
			)

			(else									;**	she dropped in
				(= egoIsPatti TRUE)			;**	Just in case
				(= normalEgoView vPatti)

				(Load VIEW vPmarijuana)
				(Load VIEW vPgettingNuts)
				(Load VIEW vPstoned)
				(Load VIEW vPlasso)
				(Load VIEW vPfallingIn)
				(Load VIEW iMarijuana)
				(Load VIEW ivRope)
		
				(Load SOUND sHighOnDrugs)
				(Load SOUND sLasso)
				(Load SOUND sHitBottom)
				(Load SOUND sJungleRegion)

				(music
					number			sFalling,
					loop				1,
					play,
				)

				(= egoState FALLING_IN)
				(ego
					ignoreActors,
					ignoreHorizon,
					view				vPfallingIn,
					setLoop			lPfallingIn,
					setCycle			Forward,
					setStep			1 8,
					setPri			ltRed,
					illegalBits		0,
					posn		 		170 -99,
					init,
				)
				(RoomScript changeState fromRm525)
			)
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(cond
			((and (== (ego view) vPlasso)
					(== (ego loop) lSwinging)
					(== (ego  cel) 1))
				(soundFX
					number			sLasso,
					loop				1 ,
					play ,			
				)
			)

			((and	(& (ego onControl) cBlue)
					(== egoState NORMAL))
				(= egoState FALLING)
				(ego posn (- (ego x) 25) (ego y))
				(self changeState startFalling)
			)

			((and	(& (ego onControl) cWhite)
					(== egoState NORMAL))
				(= egoState FALLING)
				(ego posn (+ (ego x) 25) (ego y))
				(self changeState startFalling)
			)

			((and	(& (ego onControl) cBrown)
					(== egoState NORMAL))
				(= egoState FALLING)
				(ego posn (ego x) (+ (ego y) 5))
				(self changeState startFalling)
			)

			((& (ego onControl) cLtGreen)
				(curRoom newRoom 540)
			)

			((and (== potState THROWN)
					(or	(!= holdRopeX (ego x))
							(!= holdRopeY (ego y))))
				(= potState FALLEN)
				(self changeState dropRope)
			)

			((and (== egoState NORMAL)
					(not (TstFlg drankRiverWater))
					(not onOtherSide))
				(cond
					((== secondsInRoom 30)
						(++ secondsInRoom)
						(Print "You feel a little thirsty.")
					)
					((== secondsInRoom 60)
						(++ secondsInRoom)
						(Print "You feel VERY thirsty!")
					)
					((or	( > secondsInRoom 90)
					 		(== potState TIED))
						(self changeState dyingOfThirst)
					)	
				)
			)
		)

		(super doit)
	)


	(method (handleEvent event)
;**			(if (and (not (super handleEvent event))
;**						(not (event claimed))
;**						modelessDialog
;**						demo
;**						(== (event message) ENTER)
;**						(== (event type) keyDown))
;**				(event claimed TRUE)
;**				(cls)
;**				(self cue)
;**			)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'make, weave/marijuana, rope')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					(onOtherSide
						(Print "You can't; there is no suitable fiber here!")
					)
					(else
						(switch potState
							(HERE
								(Print "Good idea, but you presently have 
								nothing with which to make a rope.")
							)
							(PICKED
								(self changeState makeArope)
							)
							(else
								(Print "You have enough rope.")
							)
						)
					)
				)
			)

			((and (>= potState TIED)
					(Said 'untie'))
				(Print "You'd be happier if you did not!")
			)

			((or	(Said 'lasso/rock')
					(Said 'throw/marijuana, rope'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(switch potState
							(HERE
								(Print "That's a thought, but what will you use?")
							)
							(PICKED
								(Print "You carelessly throw your marijuana to the
									winds.")
								(ego put iMarijuana curRoomNum)
								(= potState 0)
							)
							(ROPE
								(if (not nearCliffEdge)
									(Print "Move a little closer to the edge, Patti.")
								else
									(self changeState throwRope)
								)
							)
							(THROWN
								(ItIs)
							)
							(TIED
								(ItIs)
							)
							(FALLEN
								(Print "There's no way you can lasso that palm tree
									from over here!
									You're stuck on this side of the canyon.")
							)
						)
					)
				)
			)

			((Said 'tie/rope>')
				(cond
					((Said '//nut')
						(Print "You could never hang by your nuts!")
					)
					((!= egoState THROWING_ROPE)
						(event claimed TRUE)
					 	(Print "A good idea, but slightly premature.")
					)
					((Said '/[/noword]')
						(Print "To what?")
					)
					((Said '//tree')
						(switch potState
							(ROPE
								(Print "A good idea, but slightly premature.")
							)
							(THROWN
					  			(self changeState tieRopeToTree)
							)
							(TIED
								(ItIs)
							)
							(FALLEN
								(Print "You'll never be able to tie a knot around
									that tree from over here!")
							)
							(else
								(Print "Rope? What rope?")
							)
						)
					)
				)
			)

			((or	(Said 'make/harness, sling, belt, seat, brace')
					(Said 'tie/rope/me, self, patti')
					(Said 'use/dress')
					(Said 'tear/skirt, clothes, dress'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((== normalEgoView vPshortDress)
						(Print "You already did that.")
					)
					(( < potState THROWN)
						(Print "An excellent idea, but slightly premature.")
					)
					(( < potState TIED)
						(Print "If you did that now, 
							you'd have to turn loose of the rope!")
					)
					(( > potState TIED)
						(Print "It's too late now, Patti.")
					)
					(else
						(self changeState makeHarness)
					)
				)
			)

			((or	(Said 'cross/canyon, rope')
					(Said 'cross//canyon, rope')
					(Said 'mount, go, grab, walk, use, climb/rope, harness')
					(Said 'mount, go, grab, walk, use, climb//rope, harness'))
				(cond
					((!= egoState NORMAL)
						(Print "How can you do that?")
					)
					(onOtherSide
						(Print "It's too late for that.
							There's no way to get back to the ledge from here.")
					)
					((< potState TIED)
						(Print "Good idea, but not just yet.")
					)
					(( > throwY (ego y))
						(NotClose)
					)
					(else
						(self changeState mountRope)
					)
				)
			)

			((and onOtherSide
					(Said '/rope'))
				(Print "Your rope is hanging from the rock,
					but at such an angle and so near the edge of the
					cliff that you dare not take it.")
			)

			((and potState
					(Said 'test, look/rope, knot'))
				(Print "It might be okay.")
			)

			((and (== potState THROWN)
					(Said 'use, climb, swing/rope'))
				(Print "That's not the answer, Patti.
					If you did that, you'd crash into that cliff over there!")
			)

			((Said 'climb/tree')
				(cond
					((== egoState AT_TREE_TOP)
						(self changeState goDown)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(onOtherSide
						(Print "There are no trees near you.")
					)
					(nearWrongTree
						(Print "Move over until you are near the other tree.")
					)
					((not nearRightTree)
						(Print "There are trees here, 
							but none close enough for you to climb.")
					)
					(else
						(self changeState climbTree)
					)
				)
			)

			((and	(== egoState AT_TREE_TOP)
					(Said 'go, climb<down'))
				(self changeState goDown)
			)

			((Said 'pick, get/nut')
				(cond
					((ego has iCoconuts)
						(Print "You already have a lovely set of coconuts, Patti!")
					)
					((!= egoState AT_TREE_TOP)
						(Print "The coconuts hang below the fronds at the
							top of the tree.
							There's no way you could reach them from here, Patti.")
					)
					(else
						(self changeState grabYourNuts)
					)
				)
			)

			((Said 'climb[<down]/rock, canyon, ledge')
				(Print "There's no way you could possibly scale these rocks.")
				(Print "(Especially in that white dress!)" LOW)
			)

			((Said 'climb<up[/rock, canyon, ledge]')
				(Print "You'll never be able to return up there, Patti.")
			)

			((or	(Said 'look<down')
					(Said 'look/cliff, edge, canyon'))
				(Print "It's a long way to the bottom of that canyon.
					Be careful, Patti!")
			)

			((and (not onOtherSide)
					(or	(Said 'look/sky, hose')
							(Said 'climb/hill, cliff, mountain')
							(Said 'look<up')))
				(Print "It's a long way back up to that stream and waterfall!")
				(Print "High above your head hangs the remains of your poor
					pantyhose, twisting slowly, slowly in the wind.")
			)


			((Said 'pick, get/marijuana, plant, leaves, hemp')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(switch potState
							(HERE
								(if (not nearMarijuana)
									(NotClose)
								else
									(self changeState getDope)
								)
							)
							(PICKED
								(Print "You have all you need.
									The question is: what are you going to do with
									what you've got?")
							)
							(ROPE
								(Print "You have enough hemp rope to meet your needs.")
							)
							(else
								(Print "You need no more at this time.")
							)
						)
					)
				)
			)

			((or	(Said 'make, roll/joint')
					(Said 'light, smoke/plant, leaves, marijuana'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (ego has iMarijuana))
						(Print "You have nothing to smoke.")
					)
					(( > potState PICKED)
						(Print "It's too late to smoke it_--
							you turned it into a rope, remember?")
					)
					(else
						(= egoState SMOKING_DOPE)
						(self changeState moveToCenterStage)
					)
				)
			)

			((or	(Said 'put/dope, leaves/mouth')
					(Said 'eat, chew/plant, leaves, marijuana'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (ego has iMarijuana))
						(Print "You have no marijuana.")
					)
					(( > potState PICKED)
						(Print "It's too late for that_--
							you turned it into a rope, remember?")
					)
					(else
						(= egoState CHEWING_DOPE)
						(self changeState moveToCenterStage)
					)
				)
			)

			((Said 'untie')
				(Print "That's unnecessary.")
			)

			((Said 'move, push, shake/tree')
				(Print "Shaking won't help. 
					You must go right to the source, Patti!")
			)

			((and (ego has iCoconuts)
					(or	(Said 'use, tie/nut')
							(Said 'use, tie//nut')))
				(Print "There will come a time when you may make use of
					those coconuts.  This is just not it!")
			)

			((Said 'look>')
				(cond
					((Said '/rock')
						(cond
							((== potState FALLEN)
								(Print "Your rope is hanging from the rock,
									but at such an angle and so near the edge of the
									cliff that you dare not take it.")
							)
							((== potState TIED)
								(Print "It looks secure enough.")
								(Print "(But is it?)" LOW)
							)
							(else
								(Print "Across the canyon is a rock that rises to a
									point. Get the point?")
							)
						)
					)

					((Said '/ground')
						(Print "Two small depressions indicate your landing point.")
					)

					((Said '/tree')
						(cond
							(onOtherSide
								(Print "A path to the northwest leads through the
									trees and away from here.")
							)
							((== egoState AT_TREE_TOP)
								(Print "Since your legs are wrapped around it,
									the tree is two inches from your nose and
									quite easy to see!")
							)
							(else
								(Print "Looking up under the fronds,
									you can see that these are coconut palms.")
							)
						)
					)

					((Said '/nut')
						(if (>= filthLevel RISQUE)
							(Print "(You now realize you've been waiting for
								a chance to type that this entire game!)" LOW)
						)
						(cond
							((ego has iCoconuts)
								(event claimed FALSE)	;**	pass it to rm0
							)
							(onOtherSide
								(Print "From over here,
									you think you may be able to make out a
									faint outline of some coconuts hanging beneath
									the leaves of the palm tree to which
									you tied your rope.")
							)
							(else
								(Print "Peeking beneath the palm trees' fronds,
									you can see that these trees have large,
									brown nuts.")
								(Print "You surmise they are coconut palms.")
							)
						)
					)

					((and (not (ego has iMarijuana))
							(or	(Said '/pot')
									(Said '/plant, leave')))
						(cond
							((== egoState AT_TREE_TOP)
								(Print "Carefully peering beneath the overhanging
									fronds, you observe this is a male of the
									species.")
							)
							(else
								(Print "Dr. Nonookee, 
									the evil genius that your Leisure Suit Larry defeated in 
									\"Looking for Love (in Several Wrong Places),\"
									planted this garden many years ago.
									He must have used a helicopter for harvesting!")
							)
						)
					)

					((Said '/plant, leave')
						(cond
							((== egoState AT_TREE_TOP)
								(Print "Carefully peering beneath the overhanging
									fronds, you observe this is a male of the
									species.")
							)
							(onOtherSide
								(Print "There is a small opening in the brush
									towards the northwest.")
							)
							(else
								(Print "Wait a minute!
									You've seen those leaves in pamphlets from
									the Surgeon General's office.
									Why, that stuff's marijuana!
									You must have tumbled into what's left of
									Dr. Nonookee's private stash!")
							)
						)
					)

					((Said '[/ledge, room]')
						(cond
							((== egoState AT_TREE_TOP)
								(Print "From up here, you can see a long way.
									Across the canyon you catch a glimpse of running
									water and flashes of pink through
									the jungle growth.")
							)
							(onOtherSide
								(Print "There is a small opening in the brush
									towards the northwest.
									There's no reason to mess around here, Patti.
									Your destination lies before you.")
							)
							(else
								(Print "Your three-point landing has left you
									upon a small ledge projecting from the
									face of the cliff.
									All around you are plants with unusually
									shaped leaves.")
							)
						)
					)
				)
			)
		)
	)



	(method (changeState newState)
		(switch (= state newState)

			(intro
			)

			(fromRm525
				(ego
					ignoreActors,
					setStep			1 1,
					setMotion		JumpTo 170 155 self,
				)
			)

			(hitDirt
			 	(ego
					setLoop			lFloor2standingEL,
					setCel			0,
				)
				(music
					number			sHitBottom,
					loop				1,
					play,
				)
				(ShakeScreen 3 SHAKE_UP_DOWN)
;**					(if demo
;**						(= state (- getUp 1))
;**					)
				(delay 4)
			)

			(youshake
				(Print "You slowly recover from your near-tragic pantyhosing,
					and try to remember why you are here.")
				(delay 4)
			)

			(itsLarry
				(Print "Of course! It's Larry you're seeking.
					You bring yourself back to your feet,
					ready to again resume your quest for the man of your dreams!")
				(delay 4)
			)

			(getUp
				(ego
					setCycle	 		EndLoop self,
				)
				(addToPics
					add 				atpTits,
					doit
				)
			)

			(backToNormal
				(music
					number			sJungleRegion,
					loop				bgMusicLoops,
					play,
				)
				(ego
					posn				170 155,
					cel				1,
				)
				(NormalEgo facingDown)
				(= egoState NORMAL)
;**					(if demo
;**						(= oldGameSpeed 6)
;**						(self changeState demo1)
;**					)
				(theGame setSpeed oldGameSpeed)
			)


			;**	get some pot

			(getDope
				(if (not (TstFlg pickedPot))
					(SetFlg pickedPot)
					(theGame changeScore 10)
				)
				(Ok)
				(Print "You bend over and gather a few choice
					products from Dr. Nonookee's garden of unearthly delights.")
				(HandsOff)
				(= moveCounter 0)
				(ego
					view				vPmarijuana,
					cycleSpeed		1,
					setStep			2 1,
					setLoop			lBendingOver,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(pickDope
				(ego
					cycleSpeed		0,
					setLoop			lPickingDope,
					setCycle			Forward,
					setMotion		MoveTo (Random 111 195) (ego y) self,
				)
			)

			(smallDelay
				(= cycles 11)
				(if ( > 4 (++ moveCounter))
					(= state (- pickDope 1))
				)
			)

			(stanDup
				(ego
					cycleSpeed		1,
					setLoop			lBendingOver,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(pickDopeDone
				(NormalEgo)
				(= potState PICKED)
				(ego
					loop				facingDown,
					get				iMarijuana,
				)
				(Print "Are you sure this is something you should be doing?")
			)


			;**	If she forgot to drink from the stream in 510, she's dead meat.

			(dyingOfThirst
				(HandsOff)
				(= egoState NEAR_DEATH)
				(Print "You feel your precious bodily fluids turn to dust!")
				(ego
					loop			facingSouth,
				)
				(music fade)
				(delay 3)
			)

			(hitTheDeck
				(ego
					view			vPfallingIn,
					loop			lFloor2standingEL,
					setCel		LastCel,
					setCycle		BegLoop self,
				)
			)

			(deadOfThirst
 				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vPdeadOfThirst,
					register 	(Format @string  "You were so near...
										and yet, so far!"),
				 	next			(Format @string2 "A Little \"Dry\" Humor")
				)
			)


			;**	This handles Patti "using"

			(moveToCenterStage
				(Ok)
				(HandsOff)
				(ego
					setMotion		MoveTo 151 142 self,
				)
				(music fade)
			)

			(onYourKneesBitch
				(ego
					view				vPmarijuana,
					loop				lKneeling,
					cel				0,
					setCycle			EndLoop self,
				)
				(delay 3)
			)

			(takeAwayPoints
				(theGame changeScore -50)
				(if (== egoState CHEWING_DOPE)
					(Print "You fill your mouth with marijuana leaves and
						slowly chew them..."
						HIGH
						#icon iMarijuana 0 0)
					(= state (- buzz 1))
				else
					(Print "You quickly dry a few leaves in the sun,
						pull a pack of rolling papers from your bra
						and place the leaves in the paper
						while professionally executing a one-handed roll."
						HIGH
						#icon iMarijuana 0 0)
				)
				(delay 3)
			)

			(smokeDope
				(ego
					loop				lMakingJoint,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(getRocks
				(Print "Grabbing a couple of rocks,
					you slap them together until they make a spark that
					ignites your joint."
					#dispose
					HIGH)
				(= moveCounter 0)
				(ego
					loop				lGetRocks,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(sparking
				(ego
					loop				lSparking,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(sparkDelay
				(if (> 3 (++ moveCounter))
					(= state (- sparking 1))
				)
				(= cycles 20)
			)

			(cyclingForward
				(ego
					loop				lPuffing,
					cel				0,
					setCycle			Forward,
				)
				(= cycles (* 3 (NumCels ego)))
			)

			(inhaleIt
				(ego
					loop				lDeepInhale,
					cel 				0,
					setCycle			EndLoop self,
				)
			)

			(getHappy
				(ego
					loop				lSmiling,
					cel				0,
					setCycle			EndLoop ,
				)
				(= cycles 44)
			)

			;**	This section of code is shared by both smoking and chewing:

			(buzz
				(Print "You begin to get a little buzz on."
					#dispose
					HIGH)
				(music
					number			sHighOnDrugs,
					loop				FOREVER,
					play,
				)
				(delay 3)
			)

			(liftOff
				(theGame setSpeed 6)
				(ego
					view				vPstoned,
					posn				(ego x) (- (ego y) 26),
					cycleSpeed		1,
					loop				lReclining,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(clearedGround
				(ego
					put				iMarijuana LIMBO,
					setLoop			lFloating,
					cel				0,
					illegalBits		0,
					ignoreActors,
					setPri,
					setStep 			1 1,
					setCycle			Forward,
					setMotion		MoveTo 232 91 self,
				)
				(= cycles 11)
			)

			(whoa
				(Print "Whoa! What's happening?"
					#dispose
					HIGH)
				(= cycles 11)
			)

			(feelsLikeFloating
				(Print "This stuff makes you feel like you're floating."
					#dispose
					HIGH)
			)

			(clearOfCliff
				(Print "Looking down, you notice the ground seems far below you."
					#dispose
					HIGH)
				(ego
					setPri			green,
					setMotion		MoveTo 49 91 self,
				)
				(= cycles 22)
			)

			(farOut
				(Print "Hey, cool, dude!"
					#dispose
					HIGH)
				(= cycles 22)
			)

			(farOut2
				(Print "(You see visions of Daventry!)"
					#dispose
					LOW)
			)

			(wearingOff
				(music				fade)
				(Print "Aw, bogus! Is this stuff wearing off already?"
					#dispose
					HIGH)
				(delay 3)
			)

			(roadRunnerTake
				(= cycles 0)
				(delay 0)
				(Print (Format @string "%s" expletive)
					#dispose
					HIGH)
				(music
					number			sFalling,
					loop				1,
					play,
				)
				(ego
					setLoop			lWearingOff,
					cel				0,
					cycleSpeed		0,
					setCycle			EndLoop self,
				)
				(= state startFalling)
			)


			;**	Climb the palm tree

			(climbTree
				(Ok)
				(HandsOff)
				(Print "You wrap your slender thighs tightly around the long,
					cylindrical trunk.")
				(if (>= filthLevel DIRTY)
					(Print "(...an experience not wholly unfamiliar to you.)" LOW)
				)
				(= egoState CLIMBING_TREE)
				(ego
					view				vPgettingNuts,
					setLoop			lClimbingTree,
					illegalBits		0,
					posn				129 116,
					setPri			11,
					cycleSpeed		1,
					moveSpeed		1,
					setMotion		MoveTo treeTopPosn self,
				)
			)

			(StopCycling
				(= egoState AT_TREE_TOP)
				(User canInput	TRUE)
			)


			;**	she gets her nuts

			(grabYourNuts
				(Ok)
				(HandsOff)
				(ego
					setLoop			lGettingNutsL,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(verySmallDelay
				(ego
					setLoop			lClimbingTree,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(pickNuts2
				(ego
					setLoop			lGettingNutsR,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(sitAround
				(ego
					get				iCoconuts,
					setLoop			lClimbingTree,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(stillUpTree
				(theGame changeScore 25)
				(Print "Reaching beneath the palm tree's frond kilt,
					you grab a pair of the large brown hairy nuts.")
				(if (>= filthLevel DIRTY)
					(Print "...an experience not wholly unfamiliar to you!" LOW)
				)
				(User canInput	TRUE)
			)


			;**	Patti climbs down the tree

			(goDown
				(Ok)
				(HandsOff)
				(ego
					cycleSpeed		1,
					setLoop			lClimbingTree,
					setCycle			Walk,
					setMotion		MoveTo 129 116 self,
				)
			)

			(onBottom
				(ego
					posn				128 132,
				)
				(NormalEgo facingUp)
				(= egoState NORMAL)
			)


			;**	Here's where we make the rope:

			(makeArope
				(Ok)
				(HandsOff)
				((Inventory at iMarijuana)
					view				ivRope,
;**						said				'/marijuana, rope'
				)
				(Format ((Inventory at iMarijuana) name) "Hemp Rope")
				(theGame changeScore 100)
				(Print "Cleverly remembering that an original, legal use
					of marijuana was in the manufacture of hemp rope,
					you take the marijuana you harvested and carefully
					weave it into a rope.
					Fortunately, you picked enough so your rope is just
					slightly longer than the distance across that chasm."
					HIGH
					#icon iMarijuana 0 0)
				(delay 3)
			)

			(ropeMaid
				(Print "In other words, our little Muhammad Ali has made her own
					\"dope-a-rope.\"")
				(= potState ROPE)
				(NormalEgo)
			)


			;**	throw the rope

			(throwRope
				(HandsOff)
				(Ok)
				(= egoState THROWING_ROPE)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo throwPosn self,
				)
			)

			(windUp
				(music stop)
				(ego
					view				vPlasso,
					posn				holdRopeX holdRopeY,
					setLoop			lSwinging,
					cel				0,
					setCycle			Forward,
				)
				(= cycles (* 3 (- (NumCels ego) 1)))
			)

			(tryAndMiss
				(ego
					setLoop			lMissing,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(delayAfterMiss
				(if ( > 2 (++ attempts))
					(= state (- windUp 1))
				)
				(delay 3)
			)

			(lastTry
				(Print "Giving it everything you've got,
					you hurl the rope as hard as you can." HIGH)
				(ego
					setLoop			lSwinging,
					setCycle			Forward,
				)
				(= cycles (* 3 (- (NumCels ego) 1)))
			)

			(ropeOnRock
				(ego
					setLoop			lMadeIt,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(waitingToTie
				(theGame changeScore 20)
				(Print "You did it! You've lassoed that rock on the far side
					of the canyon." BIG)
				(= potState THROWN)
				(soundFX stop)					
				(music				play)
				(HandsOn)
			)


			;**	tie rope to tree

			(tieRopeToTree
				(= potState TIED)
				(theGame changeScore 20)
				(Ok)
				(HandsOff)
				(ego
					view				vPlasso,
					posn				throwPosn,
					setPri			ltBlue,
					setLoop			lTying,
					setCycle			Forward,
				)
				(aRope
					view				vRopeOfDope,
					setLoop			lRopeTightening,
					cel				0,
					posn				ropePosn,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(tinyDelay
				(aRope				stopUpd)
				(= cycles 10)
			)

			(doneTying
				(Print "Although your previous experience with knots 
					has been limited to fashion accessories 
					(plus a few weekends spent on yachts in ports around the world
					serving as deck hand au naturel),
					you tie the rope to the palm tree as securely as you can."
					BIG)
				(NormalEgo facingLeft)
				(ego put iMarijuana LIMBO)
				(= egoState NORMAL)
			)


			;**	make a harness

			(makeHarness
				(HandsOff)
				(Ok)
				(ego
					view				vPlasso,
					setLoop			lTearingDress,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(allDone
				(theGame changeScore 50)
				(Print "Good idea, Patti!
					You discreetly remove approximately eight inches from the hem
					of your dress; just enough so when you climb on your
					rope you'll have a safety harness for security!"
					BIG)
				(= normalEgoView vPshortDress)
				((Inventory at iDress) view ivDressShort)
				(NormalEgo facingDown)
			)


			;**	Patti drops the rope

			(dropRope
				(HandsOff)
				(ego
					posn				throwPosn,
				)
				(NormalEgo facingRight)
				(aRope
					view				vRopeOfDope,
					posn				ropePosn,
					cycleSpeed		1,
					setLoop			lRopeFalling,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(youreDead
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		ivRope,
					register 	(Format @string "Oh, Patti.
										Without that rope, 
										you'll never make it across the canyon!"),
					next			(Format @string2 "Hey! Hold still, willya?")
				)
			)


			;**	Mount the rope

			(mountRope
				(Ok)
				(HandsOff)
				(= egoState ON_ROPE)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo throwPosn self,
				)
			)

			(putOnHarness
				(if (!= normalEgoView vPshortDress)
					(self cue)
				else
					(Print "Before climbing onto the rope, you slip into your
						safety harness.
						Will it be strong enough to hold you?" HIGH)
					(ego
						view				vPlasso,
						setLoop			lHarness,
						cel				0,
						cycleSpeed		1,
						setCycle			EndLoop self,
					)
				)
			)

			(mountUp
			 	(ego
			 		view				vPlasso,
					setLoop			lMountingRope,
					cel				0,
					posn				mountPosn,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(gotoNextRoom
				(Print "You wrap your long, delicate, pianist's fingers 
					and your long, delicate, pianist's legs
					around your handmade rope.
					Squeezing your legs tightly together,  
					(an experience wholly unfamiliar to you),
					you move out over the canyon.")
				(music fade)
				(delay 3)
			)

			(changeRooms
				(curRoom newRoom 535)
			)


			;**	Handle tripping over the edge of the cliff:

			(startFalling
				(HandsOff)
				(Print (Format @string "%s" expletive) HIGH #dispose)
				(music
					number			sFalling,
					loop				1,
					play,
				)
				(ego
					view				vPfalling,
					setLoop			(if onOtherSide lFallingSmall else lFalling),
					illegalBits		0,
					ignoreActors,
					setPri			magenta,
					setStep			1 8,
					cel				0,
					cycleSpeed		0,
					setCycle			EndLoop self,
				)
			)

			(startThefall
				(ego
					setMotion		theJump
				)
				(theJump
					y					300,
				)
			)

			(fallDone
				(cls)
				(if (or	(== egoState SMOKING_DOPE)
							(== egoState CHEWING_DOPE))
					(Print "Remember, Patti: next time\n\"Just Say NO!\"")
				else
					(Print "Way to go, Newton!")
				)
				(if debugging
					(NormalEgo)
					(= egoState NORMAL)
					(if onOtherSide
						(ego
							posn 		210 77,
							setStep	2 1,
						)
					else
						(ego posn 159 158)
					)
				else
					(theGame setScript (ScriptID DYING))
					((ScriptID DYING) 
						caller		vPdeadFalling,
						register 	(Format @string "Who told you the law of
							gravity had been repealed?"),
					)
				)
			)


			;**	Demo only stuff:

;**				(demo1
;**					(Print "We're glad you \"dropped in\" for this short demo."
;**						BIG #dispose #time 6
;**					)
;**					(delay (+ 2 8))
;**				)
;**	
;**				(demo2
;**					(cls)
;**					(Print "We hope you've enjoyed your visit to Nontoonyt Island.
;**						As Passionate Patti loves to say, \"Thanks for coming!\""
;**						BIG #dispose #time 7)
;**					(delay (+ 2 7))
;**				)
;**	
;**				(demo3
;**					(cls)
;**					(curRoom newRoom 120)
;**				)
;**	
;**				(demo4
;**					(cls)
;**					(curRoom newRoom 120)
;**				)
		)
	)
)

(instance aRope of Prop
	(properties
		view 			vRopeOfDope
		loop			lRopeFalling
		x				999
		y				1111
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			white,
		)
	)
)
(instance RopeScript of Script
	(method (cue)
		(aRope		stopUpd)
		(Print "Patti! Your knot around the palm tree came loose.
			Now there's no way for you to turn back!" BIG)
	)
)

(instance atpTits of PicView
	(properties
		view			vPgettingNuts
		loop			lTits
		cel			0
		x	 			169
		y				157
		priority		7
		signal		ignrAct
	)
)

(instance theJump of Jump
	(method (init)
		(super init ego RoomScript)
		(self
			yStep 	2,
		)
	)
)
