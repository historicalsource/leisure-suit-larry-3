;**
;**	Logics for room 324 -- Suzi Cheatem's office party
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 17, 1989
;**


(script#				324)

(public
	rm324				0
)


(include "323.sh")


(enum												;**	RoomScript state values
	bringInLarry
	imBack
	ohLarry
	mensUnderwear
	youRstunned
	LtoSofa
	LatSofa
	StoDoor1
	LisFine
	StoDoor2
	StoDoor3
	StoDoor4
	SatDoor
	walkHer4
	dropHat
	hatOnFloor
	stripSuzi
	sitDownLarry
	putHatDown
	mayIjoinU
	inYourBriefs
	raiseTail
	keepItErect
	lowerTail
	startScrewing
	openCeiling
	ringPhone
	whatsThat
	phoneDown
	suziGetsPhone
	pullPhoneToHead
	talkOnPhone
	SuziTalk2
	putPhoneBack
	putItBack
	screwSomeMore
	humping2please
	getThePad
	armAtPad
	writeALittle
	putPadBack
	returnArm
	interruptionDone
	makeThePlane
	throwPlane
	throwPlane2
	atBookcase
	byWindows
	planeAtFax
	faxDone
	planeDone
	IgiveUp
	fade2black
	changeRooms
)


(local
	talkCount
	humpCount
		(enum 1									;**	humpCount values
			PHONE_ONLY
			WRITES_NOTE
			FAXES_MEMO
			GIVE_UP
		)
)

(enum 324
	HUMPING
)

(instance rm324 of Room
	(properties
		picture		323
	)


	(method (init)
		(Load VIEW vSuzi)
		(Load VIEW vL&SdoingIt)

		(Load SOUND sPhone)
		(Load SOUND sForeplay)
		(Load SOUND sInterrupted)
		(Load SOUND sDoorClosing)

		(Load SCRIPT JUMP)

		(Load PICTURE blackPicture)

		(super init)

		(addToPics
			add					atpProps,
			doit,
		)

		(aSuzi  					init)
		(aChair 					init)
		(aDoorNorth				init)
		(aDoorSouth				init)
		(aPhone					init)
		(aTrapdoor				init)

		(self
			setScript 			RoomScript
		)

		(= egoState WEARING_ZIEGFIELD)
		(= normalEgoView vLziegfield)

		(NormalEgo facingRight)
		(ego
		 	posn  				41 doorY,
			illegalBits			0,
			init,
		)
		(HandsOff)
	)
)


(instance RoomScript of Script
	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(bringInLarry
				(= cycles 22)
			)

			(imBack
				(Print "\"I'm back, Ms. Cheatem,\" you say.
					\"And I'd like my divorce papers.\"")
				(delay 3)
			)

			(ohLarry
				(Print "Suzi's eyes widen as she spies your new outfit,
					\"Ohhhhhhh, Larry!
					I had no idea you enjoyed cross-dressing too!\"")
				(Print "(What does she mean, \"too?\")" LOW)
				(delay 3)
			)

			(mensUnderwear
				(Print "\"You know, nothing turns me on like wearing mens'
					underwear!\" she cries.")
				(delay 3)
			)

			(youRstunned
				(Print "You are stunned.
					You had no idea.
					Dazed, you head for the sofa.")
				(ego
					ignoreActors,
					illegalBits		0,
					moveSpeed		1,
					cycleSpeed		1,
					setMotion		MoveTo (+ 40 (ego x)) (ego y) self,
				)
			)

			(LtoSofa
				(ego
					setMotion		MoveTo hatX 134 self,
				)
			)

			(LatSofa
				(Print "\"Yes, yes,\" Suzi says, \"do sit down, Mr. Laffer.
					Make yourself comfortable, Larry.
					You wouldn't mind if I called you Larry, would you?\"")
				(ego
					loop				facingSouth,
				)
				(aSuzi
					view				vSuzi,
					setPri			RELEASE,
					setLoop			RELEASE,
					setCycle			Walk,
					setMotion		MoveTo deskUR_X deskUR_Y self,
				)
				(aChair
					view				vSuziChair,
					posn				chairX chairY,
					setLoop			lChair,
					stopUpd,
				)
			)

			(StoDoor1
				(aSuzi
					setMotion		MoveTo deskUL_X deskUL_Y self,
				)
			)

			(LisFine
				(Print "\"No, Larry is fine, I suppose.\"")
				(aSuzi
					setMotion		MoveTo deskUL_X (+ deskUL_Y 16) self,
				)
			)

			(StoDoor2
				(aSuzi
					setMotion		MoveTo 47 doorY self,
				)
				(= cycles 27)
			)

			(StoDoor3
				(Print "\"Allow me to assure our confidentiality, Larry,\"
					says Ms. Cheatem.
					\"I hate for anyone to violate the privacy of my
					`attorney-client' relationships!\"")
			)

			(StoDoor4
				(aDoorNorth
					setCycle			BegLoop
				)
				(aDoorSouth
					setCycle			BegLoop self
				)
				(music fade)
			)

			(SatDoor
				(soundFX
					number			sDoorClosing,
					loop				1,
					play,
				)
				(aDoorNorth			stopUpd)
				(aDoorSouth			stopUpd)
				(Print "\"Would you mind if I slip out of this hat?\"
					you ask her.")
				(aSuzi
					setMotion		MoveTo (+ 15 (ego x)) doorY self,
				)
			)

			(walkHer4
				(Print "\"Feel free to slip out of whatever you wish,\"
					Suzi replies.")
				(aSuzi
					setMotion		MoveTo stripX stripY self,
				)
			)

			(dropHat
				(Print "You carefully remove the gigantic feathered hat from
					your head and place it at your feet.
					You may have come here expecting a divorce,
					but it looks like you're going to get
					more than `legalese'!")
				(ego
					cycleSpeed		1,
					setPri			8,
					view				vLziegfieldSitting,
					cel				0,
					setCycle			EndLoop self,
				)
				(aSuzi
					loop				facingWest,
				)
				(addToPics
					add				atpHat,
					doit,
				)
			)

			(hatOnFloor
				(Print "\"And now, it's time for me to get comfortable too,\"
					says Suzi.")
				(delay 3)
			)

			(stripSuzi
				(aSuzi
					cycleSpeed		2,
					view				vL&SdoingIt,
					setLoop			lSstripping,
					cel				0,
					setCycle			EndLoop self,
				)
				(music
					number			sForeplay,
					loop				FOREVER,
					play,
				)
			)

			(sitDownLarry
				(delay 3)
			)

			(putHatDown
				(Print "\"So,\" says Suzi,
					\"how do you like women in men's undergarments?\"")
				(delay 3)
			)

			(mayIjoinU
				(Print "You reply, \"Actually, I think I'd love you in mine!\"")
				(delay 3)
			)

			(inYourBriefs
				(Print "\"Enough of this legalistic foreplay, Larry,\"
					she says. \"What do you say we spend the rest of the afternoon
					`going through your briefs!'\"")
				(SetFlg scoredSuzi)
				(theGame changeScore 100)
				(aSuzi  				hide)
				(ego
					view				vL&SdoingIt,
					setLoop			lRaiseTail,
					setCel			0,
					cycleSpeed		3,
					posn				humpPosn,
				)
				(addToPics
					add				atpSuziClothes,
					doit,
				)
				(= cycles 22)
			)

			(raiseTail
				(ego
					setCycle			EndLoop self,
				)
			)

			(keepItErect
				(delay 3)
			)

			(lowerTail
				(ego
					setCycle			BegLoop self,
				)
			)

			(startScrewing
				(ego
					viewer			humpCycler,
					posn				humpPosn,
					setLoop			lL&Sscrewing,
					setCycle			Forward,
				)
				(= cycles 0)
				(delay 3)
			)

			(openCeiling
				(if ( > (++ humpCount) PHONE_ONLY)
					(self cue)
				else
					(aTrapdoor
						setMotion		MoveTo TrapdoorX TrapdoorY self,
					)
				)
			)

			(ringPhone
				(music				stop)
				(soundFX
					number			sPhone,
					loop				FOREVER,
					play,
				)
				(aPhone
					view 				vSuziOffice,
					setPri			ltMagenta,
					setStep			1 1,
					setLoop			lPhoneRinging,
					setCycle			Forward,
					setMotion		MoveTo phoneX phoneY self,
				)
			)

			(whatsThat
				(ego
					viewer			FALSE,
				  	setCel			0,
				)
				(if (== humpCount PHONE_ONLY)
					(Print "\"Hey, Suzi!\" you cry. \"What's that?\"")
				)
				(delay 3)
			)

			(phoneDown
				(switch humpCount
					(PHONE_ONLY
						(Print "\"Excuse me for a just a moment please;
							I seem to have a telephone call.
							May I put you on hold?\"")
					)
					(WRITES_NOTE
						(Print "\"Oh, no,\" says Suzi.
							\"I suppose I could have Roger hold my calls.\"")
						(Print "(It would be nice if someone got to hold
							something around here!)" LOW)
					)
					(FAXES_MEMO
						(Print "\"Not again!\" cries Suzi. \"I'm just SO busy!\"")
					)
					(GIVE_UP
						(Printf "\"%s\" you cry.
							\"If you're this busy,
							why don't I come again later!\"" expletive)
						(Print "(And you do mean that figuratively!)" LOW)
					)
				)
				(= cycles 12)
			)

			(suziGetsPhone
				(aSuzi
					cycleSpeed		0,
					setLoop			lSarmToPhone,
					cel				0,
					posn				headPosn,
					setPri			9,
					setCycle			EndLoop self,
					show,
				)
			)

			(pullPhoneToHead
				(aSuzi
					setLoop			lPhoneToHead,
					cel				0,
					setCycle			EndLoop self,
				)
				(aPhone
					setLoop			lPhone,
					setCel			cPad,
					stopUpd,
			 	)
				(soundFX				stop)
				(= talkCount 0)
			)

			(talkOnPhone
				(aSuzi
					setLoop			lStalking,
					setCycle			Forward,
				)
				(if (== humpCount GIVE_UP)
					(self changeState IgiveUp)
				else
					(= cycles (Random 11 33))
				)
			)

			(SuziTalk2
				(aSuzi
					setCel			cMouthShut,
				)
				(= cycles (Random 5 11))
				(if ( > 3 (++ talkCount))
					(= state (- talkOnPhone 1))
				)
			)

			(putPhoneBack
				(aSuzi
					setLoop			lPhoneToHead,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(putItBack
				(Print "\"Now, dear,\" Suzi says to you, \"where were we?\"")
				(aPhone
					setCel			cBoth,
					stopUpd,
				)
				(if (== humpCount PHONE_ONLY)
					(aPhone
						setMotion		MoveTo (aPhone x) -111,
					)
				)
				(aSuzi
					setLoop			lSarmToPhone,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
				(music
					number			sForeplay,
					loop				FOREVER,
					play
				)
			)

			(screwSomeMore
				(aSuzi				hide)
				(ego
					viewer			humpCycler,
					setCycle			Forward,
				)
				(delay 2)
			)

			(humping2please
				(switch humpCount
					(PHONE_ONLY
						(Print "You think to yourself,
							\"I've had lawyers do this to me before,
							but this is the first time...\"")
						(self changeState startScrewing)
					)
					(WRITES_NOTE
						(Print "\"Oh, no!\" says Suzi.
							\"I'd better write myself a note before that
							conversation slips my mind.\"")
						(Print "(It would be nice if something started
							slipping around here!)" LOW)
					)
					(FAXES_MEMO
						(Print "You've had just about all of this you can take!")
					)
				)
				(delay 2)
			)

			;**	This section is only done the 2nd & 3rd phone calls.

			(getThePad							;**	Suzi gets the pad of paper
				(ego
					viewer			FALSE,
					setCel 			0,
				)
				(aSuzi
					setLoop			lSarmToPhone,
					cel				0,
					setCycle			EndLoop self,
					show,
				)
			)

			(armAtPad
				(aSuzi
					setLoop			lSgettingPad,
					cel				0,
					setCycle			EndLoop self,
				)
				(aPhone
					setCel			cPhone,
					stopUpd,
				)
			)

			(writeALittle
				(aSuzi
					setLoop			lSwriting,
					setCycle			Forward,
				)
				(delay 4)
			)

			(putPadBack
				(aSuzi
					setLoop			lSgettingPad,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(returnArm
				(aSuzi
					setLoop			lSarmToPhone,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(interruptionDone
				(Print "\"There. All done.\"")
				(aPhone
					setLoop			lPhone,
					setCel			cBoth,
					setMotion		MoveTo (aPhone x) -111 ,
				)
				(if ( < humpCount FAXES_MEMO)
					(self changeState startScrewing)
				else
					(delay 2)
				)
			)

			(makeThePlane
				(Print "\"Oh, wait!\" she says,
					\"I'd better fax Roger a memo about that last conversation!\"")
				(delay 3)
			)

			(throwPlane
				(aSuzi
					setLoop			lSthrowingPlane,
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(throwPlane2
				(ego
					viewer			humpCycler,
					setCycle			Forward,
				)
				(aSuzi
					view				vSuziOffice,
					setLoop			lAirPlane,
					setCel			0,
					posn				141 102,
					setPri			15,
					setStep			4 4,
					setMotion		MoveTo 215 74 self,
				)
			)

			(atBookcase
				(aSuzi
					setCel			1,
				   setMotion		JumpTo 289 121 self,
				)
			)

			(byWindows
				(aSuzi
					setCel			2,
					setStep			5 5,
				   setMotion		MoveTo 221 189 self,
				)
			)

			(planeAtFax
				(aSuzi
				   setMotion		MoveTo 177 233 self,
				)
			)

			(faxDone
				(delay 2)
			)

			(planeDone
				(aSuzi
					view				vL&SdoingIt,
					setLoop			lSarmToPhone,
					cel				0,
					posn				headPosn,
					setPri			9,
					hide,
				)
				(delay 2)
				(= state (- startScrewing 1))
			)


			;**	This is only executed when Larry gets fed up with faxing...

			(IgiveUp
				(Print "Suzi covers the phone with her hand and whispers to you,
					\"Sure, ok, good idea.
					Call me any time.
					As soon as I prepare your divorce papers,
					you may pick them up from Roger, my secretary.
					We must get together again soon.
					From what I've seen of you,
					I'd love to try to squeeze you into my...")
				(Print "\"...agenda!\"")
				(music
					number			sInterrupted,
					loop				1,
					play,
				)

				;**	Divorce will be ready as soon as you pass thru 320.
				(= lawyerState LSwaiting4divorce)	
				(= cycles 22)
			)

			(fade2black
				(curRoom drawPic blackPicture IRISIN)
				(cast eachElementDo #hide)
				(= cycles 22)
			)

			(changeRooms
				(Print "Wriggling your way out of her grasp,
					you grab your tall feathered hat from the floor
					and slip out her office door.
					The problem is: Suzi's docket is just too full!")
				(Print "(Poor Larry. 
					You've just experienced the agony of \"clientus interruptus!)")
				(curRoom newRoom 320)
			)
		)
	)
)


(instance atpProps of PicView
	(properties
		view				vSuziChair
		loop				lDesktopStuff
		x					240
		y					132
		priority			10
		signal			ignrAct
	)
)
(instance atpHat of PicView
	(properties
		view				vL&SdoingIt
		loop				lHat
		x					hatX
		y				   hatY
		priority			14
		signal			ignrAct
	)
)
(instance atpSuziClothes of PicView
	(properties
		view				vSuziOffice
		loop				lSuziClothes
		x					stripX
		y					stripY
		priority			14
		signal			ignrAct
	)
)

(instance aChair of Prop
	(properties
		view 			vSuziChair
		loop			lWiggleLegs
		x				chairX
		y				chairY
		signal		ignrAct
	)
	(method (init)
		(super init)
		(self
			setPri		4,
			stopUpd,
		)
	)
)

(instance aSuzi of Actor
	(properties
		view					vSuziSitting
		loop					lGetPhone
		x						SuziX
		y						SuziY
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			illegalBits		0,
			setPri			ltMagenta,
			stopUpd,
		)
	)
)

(instance aPhone of Actor
	(properties
		illegalBits		0
		view 				vSuziOffice
		x					phoneX
		y					-111
	)
	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			setLoop			lPhoneRinging,
			setPri			ltMagenta,
			setStep			1 1,
			setCycle			Forward,
		)
	)
)
(instance aTrapdoor of Actor
	(properties
		illegalBits		0
		view 				vSuziOffice
;		cel				0
		x					TrapdoorX
		y					-4
	)
	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			setLoop			lTrapdoor,
			setPri			magenta,
			setStep			1 1,
		)
	)
)

(instance aDoorSouth of Prop
	(properties
		view				vSuziOffice
;		loop				lLowerDoor
		x	 				20
		y					103
	)
	(method (init)
		(super init)
		(self
			setCel			LastCel,
			setPri			13,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aDoorNorth of Prop
	(properties
		view				vSuziOffice
		x	 				41
		y					95
		loop				lUpperDoor
	)
	(method (init)
		(super init)
		(self
			setCel			LastCel,
			setPri			11,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance humpCycler of Code
	(method (doit)
		(if (not (Random 0 9))
			(ego cycleSpeed (Random 0 5))
		)
	)
)

