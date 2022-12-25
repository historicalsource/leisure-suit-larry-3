;**
;**	Logics for room 483 -- on the balcony outside Patti's Penthouse Suite
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 24, 1989
;**


(script#				483)

(include	"480.sh")


(public
	rm483				0
)


(procedure
	Print483
)


(define 	PattiBalconyX			170)
(define	PattiBalconyY			189)


(enum												;**	RoomScript state values
	intro
	PinPlace
	hesGone
	whereto
	whyMe
	tinkerBelle
	wait
	benDover
	lookingThruScope
	showYourHole
	bringInLarry
	itsHim
	moveLarry1
	moveLarry2
	tinyDelay
	hesInBamboo
	goto470
	pattisUp
	afterDelay
	listenToThatMusicAwhile
	changeRooms
)


(local
	[string 200]
)


(instance rm483 of Room
	(properties
		picture		490
	)


	(method (init)
		(HandsOff)

		(Load SOUND sPonBalcony)
		(Load SOUND sItsHim)
		(Load SOUND sPlearnsHerQuest)
		(Load SOUND sPmissesL)

		(super init)

		(music
			number			sPonBalcony,
			loop				2,
			play,
		)

		(addToPics
			add 				atpTelescope,
			doit,
		)

		(self
			setScript 		RoomScript
		)

		(aPatti				
			setPri			15,
			init,
		)

		(ego
			get				iKey,
			illegalBits		0,
			ignoreActors,
			view				vLsmall,
			posn				173 1116,
			setPri			5,
			setStep			1 1,
			cycleSpeed		1,
			moveSpeed		1,
			init,
		)

		(systemWindow
			color 			white,
			back				black,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
		(theGame setSpeed 6)
	)

	(method (handleEvent event)
		(if (and	(== (event type) keyDown)
					(== (event claimed) FALSE)
					(== (event message) `#8))
			(Print "Skipping ahead...")
			(SetFlg tookShortcut)
			(curRoom newRoom 484)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(intro
				(aPatti
					setCycle			EndLoop self,
				)
			)

			(PinPlace
				(delay 3)
			)

			(hesGone
				(Format @string "\"He's gone. He's really gone!\"")
				(= seconds (Print483))
				(aPatti
					cycleSpeed		2,
					loop				1,
					cel				0,
					setCycle			EndLoop ,
				)
			)

			(whereto
				(Format @string "\"Where could he go at this time of night?\"")
				(= seconds (Print483))
			)

			(whyMe
				(Format @string "\"Why would he leave?
					Am I blind?
					How could I let him slip through my fingers?\"")
				(= seconds (Print483))
				(aPatti
					setCycle			BegLoop,
				)
			)

			(tinkerBelle
				(aSparkle			
					init,
					ignoreActors,
					setCycle			EndLoop ,
				)
				(delay 2)
			)

			(wait
				(Format @string "\"Wait! What's that? Off in the distance.
					Way over there at the point!
					Was that a flash of white polyester?\"")
				(= seconds (Print483))
			)

			(benDover
				(aPatti
					loop				2,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(lookingThruScope
				(aPatti				stopUpd)
				(delay 3)
			)

			(showYourHole
				(aHole0
					setPri			6,
					ignoreActors,
					init,
				)
				(aHole1
					setPri			5,
					ignoreActors,
					init,
				)
				(aHole2
					setPri			4,
					ignoreActors,
					init,
				)
				(delay 3)
			)

			(bringInLarry
				(ego
					posn				173 116,
					setMotion		MoveTo 212 116 self
				)
				(= cycles 11)
			)

			(itsHim
				(music
					number			sItsHim,
					loop				2,
					play,
				)
				(Format @string "\"IT'S HIM!!\"")
				(Print483)
			)

			(moveLarry1
				(ego
					setPri			4,
					setMotion		MoveTo 212 110 self
				)
			)

			(moveLarry2
				(Format @string "\"But, where is he going?
					There's nothing that way but uncharted bamboo forest!\"")
				(= seconds (Print483))
				(ego
					setMotion		MoveTo 192 110 ,
				)
			)

			(tinyDelay
				(= cycles 11)
			)

			(hesInBamboo
				(Format @string "\"He's gone!
					But it had to be Larry.
					His pulsating pectorals are recognizable anywhere!!\"")
				(= seconds (Print483))
			)

			(goto470
				(ego					hide)
				(aHole0				dispose)
				(aHole1				dispose)
				(aHole2				dispose)
				(aPatti
					cycleSpeed		2,
					setCycle			BegLoop self,
				)
			)

			(pattisUp
				(delay 3)
			)

			(afterDelay
				(music
					number			sPlearnsHerQuest,
					loop				2,
					play,
				)
				(Format @string "\"Suddenly, 
					everything seems so obvious, so simple.
					I MUST give up everything and find my man.
					I know my quest_-- to find Larry Laffer!\"")
				(= seconds (Print483))
			)

			(listenToThatMusicAwhile
				(delay 5)
			)

			(changeRooms
				(music fade)
				(systemWindow
					color 			textColor,
					back				backgroundColor,
				)
				(TheMenuBar	
					draw,
					state 			TRUE,
				)
				(StatusLine enable)
				(ClrFlg preventAutoSave)
				(ClrFlg noCursor)
				(curRoom	newRoom 484)
			)
		)
	)
)


(instance aPatti of Actor
	(properties
		view					vPbalcony
;		loop					0
		x						PattiBalconyX
		y					   PattiBalconyY
;		illegalBits			0
 		cycleSpeed			1
	)
)

(instance atpTelescope of PicView
	(properties
		view				vBalcony
		x					160
		y					189
		priority			4
		signal  			ignrAct
	)
)
(instance aHole0 of View
	(properties
		view				vBalcony
		x					179
		y					092
		loop				1
;		cel				0
	)
)
(instance aHole1 of View
	(properties
		view				vBalcony
		x					179
		y					092
		loop				1
		cel				1
	)
)
(instance aHole2 of View
	(properties
		view				vBalcony
		x					178
		y					092
		loop				1
		cel				2
	)
)

(procedure (Print483 &tmp t )
	(Print @string
		#at 			10 5 
		#width 		290
		#mode			teJustCenter
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)

(instance aSparkle of Prop
	(properties
		view				vBalcony
		loop				lSparkle
;		cel				cSparkle
		x					216
		y					9
	)
)

