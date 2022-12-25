;**
;**	Logics for room 355 -- Chairman Kenneth's office
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 10, 1989
;**	


(script#				355)

(public
	rm355				0
)


(define	vNative'sInc				350)
	(define	lFountain			3)

(define	vKen							352)
(define	vRoom							355)
	(define	lTalk					0)
	(define	lHand					1)
	(define	lPoundingDesk		2)
	(define	lEmptyChair			3)

(define	vLroom		(+ 1 vRoom))
	(define	lLookNorth			0)
	(define	lLookSouth			1)
	(define	lSit					2)
	(define	lSlide				3)
	(define	lDesk					4)
		(define	cComputer	0)
		(define	cPaper		1)
		(define	cPhone		2)
		(define	cSign			3)

;(define	sMuzak						032)


(enum												;**	RoomScript state values
	intro
	talkKen
	pleaseSitDown
	moreKenTalk
	notOnCouch
	faceKen
	lookSouth
	faceKenAgain
	head4zebra
	onZebra
	sitDown
	slideOff
	oops
	larryInPlace
	perhaps
	heDidn'tLikeIt
	tellPlot
	kenStands
	kenTurningCornerOfDesk
	turnKenSomeMore
	doneHere
)



(instance rm355 of Room
	(properties
		picture		355
	)


	(method (init)
		(Load VIEW vKen)
		(Load VIEW vLroom)

		(super init)

		(addToPics
			add 				atpComputer,
			add 				atpPaper,
			add 				atpPhone,
			add 				atpSign,
			doit
		)

		(if (> machineSpeed pc8088)
			(aFountain				
				setCycle			Forward,
				isExtra			TRUE,
				init,
			)
		)

		(aKen					init)

		(ego
			illegalBits		0,
			posn 				290 141,
			init,
		)

		(self
			setScript 		RoomScript
		)
	)
)


(instance RoomScript of Script
	(method (changeState newState)
		(switch (= state newState)


			;**	This is the path Larry follows into Ken's office; does take;
			;**	walks to zebra and sits down, then slides to floor:

			(intro
				(ego
					setMotion		MoveTo 214 146 self,
				)
				(= cycles 22)
			)

			(talkKen
				(aKen
					setCycle			Forward,
				)
			)

			(pleaseSitDown
				(Print "\"Good morning, Mr. Laffer,\" Chairman Kenneth growls,
					\"it's so nice of you to fit a little time into your busy day
					to drop by and see us here at `Natives, Inc.'
					Please, have a seat...\"")
				(aKen
					setCel			0,
				)
				(ego
					setMotion		MoveTo 269 165 self,
				)
				(= cycles 22)
			)

			(moreKenTalk
				(aKen
					setCycle			Forward,
				)
			)

			(notOnCouch
				(Print "\"...anywhere but on my couch!\" he concludes.")
				(aKen
					setCel			0,
				)
				(ego
					view 				vLroom,
					loop 				lLookNorth,
					cel 				0,
					cycleSpeed		1,
					setCycle			EndLoop ,
				)
				(= cycles 33)
			)

			(faceKen
				(ego
					setCycle			BegLoop ,
				)
				(= cycles 11)
			)

			(lookSouth
				(ego
					loop 				lLookSouth,
					cel 				0,
					setCycle			EndLoop ,
				)
				(= cycles 33)
			)

			(faceKenAgain
				(ego
					setCycle			BegLoop ,
				)
				(= cycles 11)
			)

			(head4zebra
				(ego
					view 				normalEgoView,
					cycleSpeed		0,
					setCycle			Walk,
					setMotion		MoveTo 161 165 self,
				)
			)

			(onZebra
				(ego
					setMotion		MoveTo 165 149 self,
				)
			)

			(sitDown
				(ego
					view 				vLroom,
					loop 				lSit,
					cel 				0,
					setCycle			EndLoop self,
				)
			)

			(slideOff
				(ego
					cycleSpeed		1,
					setLoop			lSlide,
					cel				0,
					setCycle			EndLoop,
					setMotion		MoveTo 146 154 self,
				)
			)

			(oops
				(Print "Oops!")
				(delay 2)
			)

			(larryInPlace
				(aKen
					setCycle			Forward,
				)
				(delay 3)
			)

			(perhaps
				(Print "\"Perhaps you're wondering why I summoned you here,
					Laffer?\" begins Ken.
					\"It has nothing to do with that recent ad campaign of yours, 
					although by its appearance
					you didn't have a hell of a lot to do with it yourself!\"")
				(aKen
					viewer			tiradeCycler,
					loop				lHand,
				)
				(delay 3)
			)

			(heDidn'tLikeIt
				(Print "(Oops. This isn't going that well, is it, Larry?)" LOW)
				(delay 2)
			)

			(tellPlot
				(Print "\"Since you are no longer married to my daughter and
					your marketing skills are non-existent,
					I can't for the life of me think of a reason why I should
					keep you on here!\"")
				(aKen
					loop				lPoundingDesk,
				)
				(delay 3)
			)

			(kenStands
				(Print "\"In fact, I've been waiting for this moment for quite
					a long time. I know the perfect way to handle this 
					situation...\"")
				(AddActorToPic ego)
				(ego
					view				vRoom,
					loop				lEmptyChair,
					posn				(aKen x) (aKen y),
					setPri			5,
					stopUpd,
				)
				(NormalActor aKen facingNorth vKen)
				(aKen
					illegalBits		0,
					viewer			FALSE,
					posn 				30 140,
					setPri			3,
					setMotion		MoveTo 30 132 self,
				)
			)

			(kenTurningCornerOfDesk
				(aKen
					setMotion		MoveTo 40 128 self,
				)
			)

			(turnKenSomeMore
				(Print "(How you hate it when he gets that look in his eye...)" 
					LOW)
				(aKen
					setMotion		MoveTo 109 128 self,
				)
			)

			(doneHere
				(Print "\"Perhaps I could introduce you to a favorite hobby
					of mine...\"")
				(curRoom newRoom 350)
			)
		)
	)
)


(instance aFountain of Prop
	(properties
		view					vNative'sInc
		loop					lFountain
		x						157
		y						104
	)
)

(instance aKen of Actor
	(properties
		view					vRoom
		loop					0
		x						33
		y						139
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			10,
			stopUpd,
		)
	)
)

(instance tiradeCycler of Code
	(method (doit)
		(if (not (Random 0 4))
			(aKen cycleSpeed (Random 0 2))
		)
	)
)

(instance atpComputer of PicView
	(properties
		view					vRoom
		loop					lDesk
		cel					cComputer
		x						33
		y						149
		priority				11
		signal				ignrAct
	)
)

(instance atpPaper of PicView
	(properties
		view					vRoom
		loop					lDesk
		cel					cPaper
		x						67
		y						128
		priority				12
		signal				ignrAct
	)
)

(instance atpPhone of PicView
	(properties
		view					vRoom
		loop					lDesk
		cel					cPhone
		x						57
		y						124
		priority				12
		signal				ignrAct
	)
)

(instance atpSign of PicView
	(properties
		view					vRoom
		loop					lDesk
		cel					cSign
		x						63
		y						142
		priority				12
		signal				ignrAct
	)
)

