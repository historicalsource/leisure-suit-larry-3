;**
;**	Logics for room 640 -- KQ4 tonguing it
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**


(script#				640)

(public
	rm640				0
)


(define	vRoom							640)
	(define	lATPs					0)
		(define	cChest		0)
		(define	cWheel		1)
		(define	cBarrel1		2)
		(define	cBarrel2		3)
		(define	cBarrel3		4)
	(define	lCamera				1)

(define	vRoberta						643)
	(define	lTalking2
	Rosella	0)
	(define	lLoweringMegEL		1)
	(define	lTalking				2)
	(define	lBerta'sButt		3)

(define	vRosella						644)
	(define	lRstanding			0)
	(define	lRtalking			1)
	(define	lRtapping			2)
	(define	lRcrawlEast			3)
	(define	lRcrawlNorth		4)
	(define	lRsliding			5)


(define	sKQ4							640)
;(define	sSierraRegion				699)
;(define	sTitle						120)


(enum												;**	RoomScript state values
	intro
	adjustCamera
	moveCameraLeft
	moveCameraDown
	goodCamera
	nowRosie
	andAction
	startClimbing
	startSliding
	raiseMeggie
	noNoRosie
	moveRosieToStart
	tapALittle
	whatsGoingOn
	iDontKnow
	shutUp
	whoRthey
	bertaTalks2Patti
	bertaDone
	cameraOffscreen
	startLarry
	itsBerta
	ohBert
	PatBerta
	LatBerta
	whyRUhere
	droppedIn
	whyI
	whyHe
	fade2black
	adequate
	cueTheTitle
	andSo
	theyMoveIn
	changeRooms
)


(local
	theCounter
)


(instance rm640 of Room
	(properties
		picture		640
	)


	(method (init)
		(Load SOUND sKQ4)
		(Load SOUND sTitle)
		(Load PICTURE blackPicture)

		;**	DEBUGGING CODE:
		;**	
;**										(= egoIsPatti TRUE)
;**										(= normalEgoView vPbikini)
;**										(= egoName (Format @egoNameBuffer "Patti"))
		;**	
		;**	

		(super init)
		
		(addToPics
			add				atpChest,
			add				atpWheel,
			add				atpBarrel1,
			add				atpBarrel2,
			add				atpBarrel3,
			add				atpBertaButt,
			doit,
		)

		(aCamera				init)
		(aRosella			init)
		(aRoberta			init)
		(NormalActor aLarry facingEast vLhunk)
		(aLarry 
			posn				8 174,
			init,
			stopUpd,
		)

		(self
			setScript 		RoomScript
		)

		(= oldGameSpeed (theGame setSpeed 6))

		(NormalEgo facingEast)
		(ego 
			posn				12 183,
			init,
			stopUpd,
		)
		(HandsOff)
	)
)


(instance RoomScript of Script
	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
				(delay 3)
			)
	 
			(adjustCamera
				(Print "\"Truck that camera left!\"")
				(delay 2)
			)

			(moveCameraLeft
				(aCamera
					setStep			1 1,
					setMotion		MoveTo 96 189 self,
				)
			)

			(moveCameraDown
				(Print "\"No, no, no! Move back!\"")
				(aCamera
					setMotion		MoveTo 47 238 self,
				)
			)

			(goodCamera
				(aCamera				stopUpd)
				(Print "\"Good, good. That's fine. Stop right there,\"
					barks the woman in the director's chair.")
				(delay 2)
			)

			(nowRosie
				(Print "Turning to the actress on the set, she says, 
					\"Now, Rosella, what do you say we take it again from the top, 
					and this time really show me some emotion!\"")
				(music fade)
				(delay 2)
			)

			(andAction
				(Print "\"And... ACTION!!\"")
				(aRoberta
					setCycle			EndLoop ,
				)
				(music
					number			sKQ4,
					loop				1,
					play,			
				)
				(delay 3)
			)

			(startClimbing
				(aRosella
					setStep			2 2,
					moveSpeed		1,
					cycleSpeed		1,
					setMotion		MoveTo 177 86 self,
				)
			)

			(startSliding
				(aRosella
					moveSpeed		0,
					cycleSpeed		0,
					setLoop			lRsliding,
					setStep			2 4,
					setMotion		MoveTo 177 123 self,
				)
			)

			(raiseMeggie
				(aRoberta
					setCycle			BegLoop ,
				)
				(= cycles 15)
			)

			(noNoRosie
				(aRosella
					setLoop			lRstanding,
				)
				(music				fade)
				(switch (++ theCounter)
					(1
						(Print "\"CUT!! No, that's no good. 
							Try it from the right side this time!\"")
					)
					(2
						(Print "\"No. CUT! You slipped again, Rosella!\"")
					)
					(3
						(aRosella	stopUpd)
						(Print "\"CUT!! How many times must we do this, Rosella?
							You must get all the way up to that uvula!\"")
						(++ state)
					)
				)
				(= cycles 22)
			)

			(moveRosieToStart
				(aRosella
					setLoop			lRcrawlEast,
					setStep			2 1,
					moveSpeed		1,
					cycleSpeed		1,
					setMotion		MoveTo 224 115 self,
				)
				(if ( < theCounter 3) 
					(= state (- andAction 1))
				)
			)

			(tapALittle
				(aRosella
					cycleSpeed		1,
					setLoop			lRtapping,
					setCycle			Forward,
				)
				(delay 2)
			)

			(whatsGoingOn
				(Print "\"What's going on here, Patti?\" whispers Larry.")
				(delay 2)
			)

			(iDontKnow
				(Printf "\"I don't know, Larry, but that girl certainly
					seems tired of going down %sthat tongue!\""
					(if (>= filthLevel DIRTY)	{on }
					else							 	{}
					)
				)
				(aRosella
					setLoop			lRtalking,
				)
				(delay 2)
			)

			(shutUp
				(Print "\"This just isn't right!\" whines the actress on the 
					giant tongue set.
					\"It's too humid in here.
					This wool skirt keeps soaking up water.
					That goo you spread on the tongue is too slippery.
					You haven't given me my motivation for this scene yet...\"")
				(Print "\"Thank you very much, Rosella!\" 
					interrupts the woman in the director's chair.
					\"If you have any other complaints,
					please don't let me stop you!\"")
				(aRoberta
					setCycle			EndLoop self,
				)
			)
			
			(whoRthey
				(Print "\"Well, yes I do!\" she pouts,
					pointing directly at you and Larry.
					\"I thought this was a `Closed Set!'
					Who are those two people over there?\"")
				(aRosella
					setLoop			lRtapping,
				)
				(delay 3)
			)

			(bertaTalks2Patti
				(aRoberta
					setLoop			lTalking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(bertaDone
				(Print "\"CUT! BREAK!! 
					Ten minutes, people,\" she shouts to the crew.")
				(Print "With an angry glare, she turns toward you and Larry,
					\"And who are you? What are you doing here?
					How did you get in? Come over here, right now!\"")
				(aRoberta
					setCel			0,
				)
				(aRosella
					loop				lRstanding,
					stopUpd,
				)
				(aCamera
					setMotion		MoveTo 41 244 self,
				)
			)

			(cameraOffscreen
				(ego
					setMotion		MoveTo 227 187 self,
				)
				(= cycles 9)
			)

			(startLarry
				(aLarry 
					setMotion		MoveTo 239 176 self,
				)
				(= cycles 22)
			)

			(itsBerta
				(Print "As you move closer, 
					you recognize the woman in the director's chair.
					\"Why, Larry, look! It's Roberta Williams!\"")
				(= cycles 11)
			)

			(ohBert
				(Print "To her, you cry, \"Oh, Ms. Williams!
					I've loved your games for years!
					What an honor this is... 
					to finally meet my software hero!\"")
				(music
					number			sSierraRegion,
					loop				FOREVER,
					play				,
				)
				(aRoberta
					setCycle			Forward,
				)
			)

			(PatBerta
				(Print "Roberta softens noticeably, 
					\"Well, yes, I... I, uh, am Roberta Williams. 
					But who are you?\"")
				(aRoberta
					setCel			0,
				)
				(delay 3)						;**	This SHOULD do nothing.
			)										;**	it's just here for safety.

			(LatBerta
				(aLarry
					loop				facingSouth,
					forceUpd,
					stopUpd,
				)
				(Print "\"Well, Roberta, 
					I'm Passionate Patti, the world-famous lounge pianist.
					The gentleman to your right is my lover.
					His name is Larry; Larry Laffer!\"")
				(aRoberta
					setCycle			Forward,
				)
				(delay 2)
			)

			(whyRUhere
				(Print "\"Nice to meet you both,\" says Roberta,
					\"but how did you get in here?
					This is supposed to be a high-security area!\"")
				(aRoberta
					setCel			0,
				)
				(delay 2)
			)

			(droppedIn
				(Print "\"We just dropped in... literally!\" offers Larry.")
				(Print "\"Actually, Berta, we've had one hell of an
					adventure just getting here. 
					Why, I've been through a virtual maze of bamboo forest 
					with just one drink of water;
					rappelled down a sheer cliff on a pair of pantyhose;
					crossed an incredible chasm with only a handmade rope;
					fought ferocious feral swine with my underwear;
					ridden a log through a whitewater canyon; and...\"" WIDE)
				(Print "\"O.K. already! Enough, enough,\" laughs Roberta.
					\"But what about him?\"")
				(delay 2)
			)

			(whyI
				(Print "\"I, I,\" Larry stammers,
					\"I've had quite a few adventures in my day, too!\"")
				(Print "\"Oh, really?\" replies Roberta, 
					\"anything that might make an adventure game?\"")
				(delay 3)
			)

			(whyHe
				(Print "\"Of course, it would, Bert!\" you brag confidently.
					\"If you could just set us up with a little place to work
					(up to my reasonable standards, of course) 
					we would write a whole series of adventure games for you!\"")
				(delay 3)
			)

			(fade2black
				(Print "\"We could discuss this over lunch,\" Larry interrupts,
					\"I haven't eaten a thing this entire game!\"")
				(curRoom drawPic blackPicture IRISIN)
				(cast eachElementDo #hide)
				(= cycles 22)
			)

			(adequate
				(Print "As the three of you head off together, Roberta says,
					\"We do have a home on the lake; 
					would that adequately meet your needs, Patti?\"")
				(music fade)
				(delay 6)
			)

			(cueTheTitle
				(music
					number			sTitle,
					loop				FOREVER,
					play ,			
				)
				(delay 3)
			)

			(andSo
				(Print "And so it ends." #time 4 #dispose)
				(delay 5)
			)

			(theyMoveIn
				(Print "Larry and Patti move in together,
					sharing a simple programmer's shack in the mountains.
					Patti gives up her musician's life on the road 
					for the one man she found able to match her passion.
					Larry begins to tell the world his life story 
					through software...")
				(delay 3)
			)

			(changeRooms
				(curRoom newRoom 650)
			)
		)
	)
)


(instance aLarry of Actor)


(instance atpChest of PicView
	(properties
		view					vRoom
;		loop					lATPs
;		cel					cChest
		x						89
		y						142
		signal				ignrAct
	)
)
(instance atpWheel of PicView
	(properties
		view					vRoom
;		loop					lATPs
		cel					cWheel
		x						159 
		y						141
		priority 			10
		signal				ignrAct
	)
)
(instance atpBarrel1 of PicView
	(properties
		view					vRoom
;		loop					lATPs
		cel					cBarrel1
		x						250 
		y						137
		priority 			9
		signal				ignrAct
	)
)
(instance atpBarrel2 of PicView
	(properties
		view					vRoom
;		loop					lATPs
		cel					cBarrel2
		x						47 
		y						134
		priority 			9
		signal				ignrAct
	)
)
(instance atpBarrel3 of PicView
	(properties
		view					vRoom
;		loop					lATPs
		cel					cBarrel3
		x						209 
		y						135
		priority 			9
		signal				ignrAct
	)
)
(instance atpBertaButt of PicView
	(properties
		view				vRoberta
		loop				lBerta'sButt
		x	 				257
		y					166
		priority			15
	)
)

(instance aRoberta of Prop
	(properties
		view 				vRoberta
		loop				lLoweringMegEL
;		cel				0
		x					257
		y					166
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			15,
		)
	)
)

(instance aCamera of Actor
	(properties
		view				vRoom
		loop				lCamera
;		cel				0
		x					120
		y					189
		illegalBits		0
	)
)
(instance aRosella of Actor
	(properties
		view 					vRosella
		x						177
		y						126
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			illegalBits		0,
			setStep			2 1,
			setCycle			Walk,
			setLoop			lRcrawlNorth,
		)
	)
)

