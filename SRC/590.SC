;**
;**	Logics for room 590 -- potted Heroes
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**

(script#				590)

(public
	rm590				0
)


;**		This is hard to believe, but here is the stack of priorities in
;**	this scene:
;**	
;**	 2		rope
;**	 4		cageRear
;**	 5		door
;**	 6		ego (Patti, in this case)
;**	 8		rainbow
;**	 9		liquid in pot
;**	10		Larry
;**	12		cageFront
;**	13		cage descending into fluid in pot
;**	14		rope lowering cage into pot
;**	15		front bubbles in pot


(define	vDoctor						586)
	(define	lPoofingInEL		0)
	(define	lTalking				1)
	(define	lChanting			2)

(define	vRoom							590)
	(define	lCageBack			0)
	(define	lCageFront			1)
	(define	lBubbles				2)
	(define	lBubblesSimmer		3)
	(define	lBubblesBoil		4)
	(define	lBubblesFront		5)
	(define	lRope					6)

(define	vLroom						591)
	(define	lLookAtOther		0)
;	(define	lTalking				1)
	(define	lReachForOther		2)

(define	vProom						592)
;	(define	lLookAtOther		0)
;	(define	lTalking				1)
;	(define	lReachForOther		2)

(define	vMisc							593)
	(define	lPLintoPot			0)
	(define	lPLkiss				1)
	(define	lPLlookAtPlayer	2)
	(define	lPLscared			3)

(define	vMagicDoor					594)
	(define	lPgetsPen			0)
	(define	lPaimsPen			1)
	(define	lPdrawsRainbow		2)
	(define	lPdrawsBlackHole	3)
	(define	lPthruHole			4)
	(define	lRainbow				5)
	(define	lDoorAppearsEL		6)
	(define	lLcrawlsWest		7)
	(define	lLthruHole			8)

(define	vPLdeadFromCannibals		595)


;(define	sFalling						004)
;(define	sNearDanger					006)
;(define	sAfterGlow					486)


(define	CageX							164)
(define	CageY							114)

(define	bubblesX						203)
(define	bubblesY						160)

(define	RainbowPosn				169  72)
(define	PattiPosn				174 110)


(enum												;**	RoomScript state values
	intro
	itsYou
	reachForEachOther
	wasteOne
	theyKiss
	uncouple
	sitBack
	somebodyIsDone
	startTalking
	plotExposition
	poofWDin
	WDhere
	whatsHeDoing
	WDyelling
	bubbleMeBaby
	GoForIt
	simmer
	boiling
	lookScared
	lowerPot
	poofWDout
	WDpoofDone
	inLimbo
	startMagicMarker
	YouBroughtHerYouMarkHer
	drawRainbow
	rainbowVisible
	drawDoor
	openDdoor
	somebodyFinished
	lowerMarker
	youDidSomething
	rightBehind
	thruTheLookingGlass
	oooohhhhh
	larryToTheHole
	ByeLarry
	LarryInHole
	hideRainbow
	fadeToBlack
	changeRooms
)


(local
	[string  88] 
	[string2 22]
)


(instance rm590 of Room
	(properties
		picture		590
	)


	(method (init)
		(Load VIEW	vDoctor)
		(Load VIEW	vMisc)
		(Load VIEW	vMagicDoor)
		(Load SOUND sFalling)
		(Load SOUND sNearDanger)
		(Load SOUND sAfterGlow)
		(Load PICTURE blackPicture)

		(super				init)

		(addToPics
			add 				atpRope,
			doit
		)

		(aLarry				init)
		(aCageBack			init)
		(aCageFront			init)

		(self
			setScript 		RoomScript,
			style 			IRISOUT,
		)

		((Inventory at iDress) view ivDressBikini)
		(= normalEgoView vPbikini)
		(NormalEgo facingEast vProom)
		(ego
			ignoreActors,
			illegalBits		0,
			setLoop			lLookAtOther,
			cel				0,
			setPri			6,
			posn				PattiPosn,
			init,
		)
		(HandsOff)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'draw, make/door')
					(Said 'draw, use/marker'))
				(cond
					((not (ego has iMarker))
						(Print "You have nothing with which to aid your escape.")
					)
					(else
						(self changeState startMagicMarker)
					)
				)
			)

			((Said 'cast, make, use/magic, spell')
				(if (ego has iMarker)
					(Print "Yes, that could work. But how?")
				else
					(Print "Yes, that's the idea.
						But where could you find something magical?")
				)
			)

			((Said 'untie, grab, cut/rope, leather')
				(Print "You can't. The cage withstands your every attempt to
					escape.")
			)

			((Said 'throw')
				(Print "Throwing things won't help!")
			)

			((Said 'use')
				(Print "What good would that do? 
					You're trapped inside an unbreakable fortress of bamboo!")
			)

			((Said 'get')
				(Print "There's nothing within reach that will help you.")
			)

			((or	(Said 'love')
					(Said '/love'))
				(Print "You two will love each other until death;
					which appears to be only a few more minutes!")
			)

			((Said 'bend/bar')
				(Print "You can't. You're only a weak piano player!")
			)

			((Said 'hit')
				(Print "As hard as you hit the cage, it will not give!")
			)

			((Said 'swing, move/cage')
				(Print "It's just too massive. 
					You'll need more than ordinary means to escape this cage!")
			)

			((Said 'look>')
				(cond
					((Said '/rope')
						(Print "A crude yet effective rope suspends the cage
							above the village cookpot.")
					)

					((Said '/man, larry')
						(Print "He's with you again. 
							You've succeeded in your quest.
							But how will you ever honeymoon together?")
					)

					((and (cast contains aDoctor)
							(Said '/native'))
						(Print "How nice! She's invited you over for dinner!")
					)

					((Said '/village')
						(Print "In the distance, 
							the villagers are preparing to have you for dinner_--
							literally!")
					)

					((Said '/cage, bar')
						(Print "The cage is made of bamboo and leather with a
							door to the rear that is securely tied shut.")
					)

					((Said '/pot')
						(Print "Peering between the bamboo poles you see your
							future coming to a short boil.")
					)

					((Said '/bamboo, cage')
						(Print "This cage is so strong you'll never break out!")
					)

					((Said '[/room]')
						(Print "At last, you are again with your Larry;
							but under such circumstances?!")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(ego
					setCycle			EndLoop,
				)
				(aLarry
					setCycle			EndLoop,
				)
				(delay 2)
			)

			(itsYou
				(Print "\"LARRY! IT'S YOU!!\""
					#at		10 10
					#width	140
					#dispose
				)
				(Print "\"PATTI! IT'S YOU!!\""
					#at		170 10
					#width	140
				)
				(cls)
				(ego
					setLoop			lTalking,
					setCycle			Forward,
				)
				(aLarry
					setLoop			lTalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(reachForEachOther
				(Print "\"Oh, Larry! I've missed you so!!\""
					#at		10 10
					#width	140
					#dispose
				)
				(Print "\"Oh, Patti! I've missed you so!!\""
					#at		170 10
					#width	140
				)
				(cls)
				(ego
					setLoop			lReachForOther,
					cel				0,
					setCycle			EndLoop self,
				)
				(aLarry
					setLoop			lReachForOther,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(wasteOne
			)

			(theyKiss
				(ego
					view				vMisc,
					setLoop			lPLkiss,
					cel				0,
					setCycle			EndLoop,
				)
				(aLarry 				hide)
				(delay 4)
			)

			(uncouple
				(ego
					setCycle			BegLoop self,
				)
			)

			(sitBack
				(ego
					view				vProom,
					setLoop			lReachForOther,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
				(aLarry
					view				vLroom,
					setLoop			lReachForOther,
					setCel			LastCel,
					setCycle			BegLoop self,
					show,
				)
			)

			(somebodyIsDone
			)

			(startTalking
				(ego
					setLoop			lTalking,
					setCycle			Forward,
				)
				(aLarry
					setLoop			lTalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(plotExposition
				(ego
					setCel			0,
				)
				(aLarry
					setCel			0,
				)
				(music fade)
				(Print "\"Oh, Larry, I just couldn't believe you left!
					You can't believe how it felt:
					to have finally found a perfect match
					and then to have you disappear into thin air!
					I awakened to an empty bed, rushed to the balcony,
					and saw you disappear through that horrible bamboo forest,
					so I started out after you.
					And I found you!!
					We're together again, at last, and this time, to stay!\""
					BIG)
				(music
					number			sAfterGlow,
					loop				FOREVER,
					play ,			
				)
				(Print "\"But, tell me, Larry: why did you go?
					Whatever caused you to leave like that?\""
					HIGH)
				(Print "\"You. You did, Patti.
					After the most fantastic night of my life,
					just as I was drifting away into a blissful sleep,
					I heard you murmur one word, `Arnold!'
					Why, Patti?
					If I'm so special,
					why would you speak someone else's name?
					Weren't you thinking of him while we made love?\""
					BIG)
				(Print "\"Oh, Larry, you wonderful, fragile fool!
					I was thinking of him because I was trying to figure out
					how to tell him I was going to leave him for you!!\""
					BIG)
				(Print "\"Patti! I am a fool. But what's the difference?
					At last we understand: we both feel the same for each other.
					We'll be together for the rest of our lives!\""
					BIG)
				(Print "\"Speaking of which,
					you did come here to rescue me, didn't you?
					You do have some sort of plan, don't you?
					You didn't travel all this way just so you could join me
					for dinner_-- in their stomachs, did you?!!\""
					BIG)
				(Print "\"Er, uh, well, just a minute,\" you stall,
					\"I'm sure I'll think of something!\""
					HIGH)
				(Print "\"You'd better think fast,\" Larry responds,
					\"I think these gals have got something cooking...
					and it's gonna be us!\""
					HIGH)
				(User canInput	TRUE)
				(delay 15)
			)


			;**	wait for use of marker; if get here, they're almost goners!


			(poofWDin
				(music
					number			sNearDanger,
					loop				FOREVER,
					play,
				)
				(aDoctor
					init,
					setCycle			EndLoop self,
				)
			)

			(WDhere
				(aDoctor
					cycleSpeed		0,
					setLoop			lChanting,
					setCycle			Forward,
				)
				(= cycles 33)
			)

			(whatsHeDoing
				(Print "\"LARRY! LOOK!!\" you cry.
					\"What's that witch doctor doing?!\""
					HIGH)
				(= cycles 33)
			)

			(WDyelling
				(aDoctor
					setLoop			lTalking,
				)
				(= cycles 33)
			)

			(bubbleMeBaby
				(aDoctor
					setLoop			lChanting,
				)
				(aBubblesFront		init)
				(= cycles 33)
			)

			(GoForIt
				(HandsOff)
				(Print "\"That's it! We're finished!!\""
					HIGH)
				(ego
					view				vMisc,
					cycleSpeed		3,
					setLoop			lPLlookAtPlayer,
					cel				0,
					setCycle			EndLoop self,
				)
				(aLarry				hide)
			)

			(simmer
				(aBubblesFront
					setLoop			lBubblesSimmer,
				)
				(delay 3)
			)

			(boiling
				(aBubblesFront
					setLoop			lBubblesFront,
				)
				(aBubblesRear		init)
				(ego
					view				vMisc,
					setLoop			lPLscared,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(lookScared
				(ego
					posn				CageX CageY,
					setLoop			lPLintoPot,
					setPri			13,
				)
				(aCageFront			hide)
				(aCageBack			hide)
				(delay 3)
			)

			(lowerPot
				(aLarry
					posn				163 53,
					view				vRoom,
					setLoop			lRope,
					setMotion		MoveTo 163 199,
					setPri			14,
					show,
				)
				(ego
					cycleSpeed		0,
					setMotion		MoveTo CageX 161 self,
				)
			)

			(poofWDout
				(ego
					setMotion		MoveTo CageX 231 ,
				)
				(aDoctor
					setLoop			lPoofingInEL,
					setCel			LastCel,
					cycleSpeed		1,
					setCycle			BegLoop ,
				)
				(delay 5)
			)

			(WDpoofDone
				(aDoctor				hide)
			 	(theGame setScript (ScriptID DYING))
			 	((ScriptID DYING)
			 		caller		vPLdeadFromCannibals,
			 		register 	(Format @string "Mark my words, Patti:
			 							sometimes you must fight magic with magic!"),
					next			(Format @string2 "Boy, are you two stewed!"),
			 	)
			)

			(inLimbo
			)


			;**	MagicMarker stuff

			(startMagicMarker
				(HandsOff)
				(Ok)
				(theGame changeScore 500)
				(Print "\"Larry, I don't know if this will work or not,
					but the only thing I can think of is this `Magic' marking
					pen I picked up back at the lounge.
					Let me see if I can draw us a way out of this mess!\""
					BIG)
				(aLarry
					loop				lTalking,
					cel				0,
					stopUpd,
				)
				(delay 0)
				(= cycles 0)
				(ego
					cycleSpeed		1,
					view				vMagicDoor,
					setLoop			lPgetsPen,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(YouBroughtHerYouMarkHer
				(ego
					setLoop			lPaimsPen,
					cel				0,
					setCycle			EndLoop self,
				)						
			)

			(drawRainbow
				(ego
					setLoop			lPdrawsRainbow,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(rainbowVisible
				(aBubblesFront
					init,
					view				vMagicDoor,
					setLoop			lRainbow,
					cel				0,
					cycleSpeed		0,
					setPri			8,
					posn				RainbowPosn,
					setCycle			Forward,
				)
				(ego
					setCycle			BegLoop self,
				)
			)

			(drawDoor
				(ego
					setLoop			lPdrawsBlackHole,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(openDdoor
				(aBubblesRear
					init,
					view				vMagicDoor,
					setLoop			lDoorAppearsEL,
					cel				0,
					cycleSpeed		1,
					setPri			5,
					posn				RainbowPosn,
					setCycle			EndLoop self,
				)	
				(ego
					setCycle			BegLoop self,
				)
			)

			(somebodyFinished
			)

			(lowerMarker
				(aBubblesRear		stopUpd)
				(ego
					setLoop			lPaimsPen,
					setCel			LastCel,
					setCycle			BegLoop ,
				)
				(delay 3)
			)

			(youDidSomething
				(Print "\"PATTI! You did it.
					At least, you did something.
					What is that? A magic door?\""
					BIG)
				(Print "\"I don't know, Larry, but from the looks of things
					hereabouts, I don't want to stay around here.
					Come on, let's get out of here!\""
					BIG)
				(= cycles 22)
			)

			(rightBehind
				(Print "\"I'm right behind you, honey,\" Larry responds.
					\"See you on the other side!\""
					BIG)
				(= cycles 22)
			)

			(thruTheLookingGlass
				(ego					hide)
				(aBubblesRear
					cycleSpeed		1,
					setLoop			lPthruHole,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(oooohhhhh
				(Format @string "\"%s\"" expletive)
				(Print @string HIGH)
				(soundFX
					number			sFalling,
					loop				1,
					play,
				)
				(Print "\"PATTI!\"" HIGH)
				(= cycles 11)
			)

			(larryToTheHole
				(aLarry
					cycleSpeed		0,
					moveSpeed		0,
					view				vMagicDoor,
					setLoop			lLcrawlsWest,
					cel				0,
					setCycle			Forward,
					setStep			2 2,
					setPri			10,
					posn				188 104,
					setMotion		MoveTo 156 104 self,
				)
			)

			(ByeLarry
				(aLarry				hide)
				(aBubblesRear
					view				vMagicDoor,
					setLoop			lLthruHole,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(LarryInHole
				(music 				stop)
				(soundFX
					number			sFalling,
					loop				1,
					play,
				)
				(aBubblesRear
					setLoop			lDoorAppearsEL,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(hideRainbow
				(aBubblesFront		hide)
				(= cycles 22)
			)

			(fadeToBlack
				(curRoom drawPic blackPicture IRISIN)
				(cast eachElementDo #hide)
				(delay 3)
			)

			(changeRooms
				(curRoom newRoom 600)
			)
		)
	)
)


(instance atpRope of PicView
	(properties
		view				vRoom
		loop				lRope
;		cel				0
		x					163
		y					53
		priority			2
		signal			ignrAct
	)
)
(instance aCageFront of View
	(properties
		view				vRoom
		loop				lCageFront
;		cel				0
		x					CageX
		y					CageY
	)
	(method (init)
		(super init)
		(self
			setPri			12,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aCageBack of View
	(properties
		view				vRoom
		loop				lCageBack
;		cel				0
		x					CageX
		y					CageY
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			4,
			stopUpd,
		)
	)
)

(instance aBubblesFront of Prop
	(properties
		view 				vRoom
		loop				lBubbles
		x					bubblesX
		y					bubblesY
	)
	(method (init)
		(super init)
		(self
			setPri			15,
			setCycle			Forward,
			ignoreActors,
		)
	)
)
(instance aBubblesRear of Prop
	(properties
		view 				vRoom
		loop				lBubblesBoil
		x					bubblesX
		y					bubblesY
	)
	(method (init)
		(super init)
		(self
			setPri			9,
			setCycle			Forward,
			ignoreActors,
		)
	)
)

(instance aLarry of Actor
	(properties
		view 				vLroom
		loop				lLookAtOther
;		cel				0
		x					173
		y					110
	)

	(method (init)
		(super init)
		(self
 			setPri			10,
			illegalBits		0,
			ignoreActors,
		)
	)
)

(instance aDoctor of Prop
	(properties
		view 				vDoctor
		loop				lPoofingInEL
;		cel				0
		x					288
		y					72
		cycleSpeed		1
	)
)

