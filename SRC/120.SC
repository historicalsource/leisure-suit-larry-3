;**
;**	Logics for room 120 -- Sierra presents / Titles
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 18, 1989
;**


(script#				120)


(public
	rm120				0
	ShadowPrint		1
)

(procedure
	ShadowPrint
)


(define	vSierra						110)
	(define	lSierra				0)
	(define	lPresents			1)
	(define	lHalfDome			2)
	(define	lDissolve			3)
										
(define	vRoom							120)
	(define	lThigh				0)
	(define	lCalf					1)
	(define	lATPleg				2)


;(define	sFanfare						110)
;(define	sTitle						120)


(define	pTitle						120)


;(define	MASH_18						9)


(enum												;**	RoomScript state values
	intro
	startTheMusic
	startDissolve
	dissolveDone
	startSierra
	sierraDone
	startPresents
	presentsDone
	pauseBetweenTunes
	changePix
	andTitle
	pattiTitle1
	pattiTitle2
	pattiTitle3
	pumpLeg
	gotoCartoon
)


(local
	heardSong
	[string 20]
)


(instance rm120 of Room
	(properties
		picture		pTitle
		style			WIPERIGHT
	)

	(method (init)
		(HandsOff)
		(theGame setSpeed 6)
;		(= egoIsPatti FALSE)
		(StatusLine	disable)
		(TheMenuBar	hide)
		(SetFlg cantSave)
		(SetFlg noCursor)
		(SetFlg preventAutoSave)

		(Load VIEW vSierra)
		(Load VIEW vRoom)

		(Load SOUND sFanfare)
		(Load SOUND sTitle)

		(Load FONT MASH_18)

		(super init)

		(addToPics
			add 				atpLeg1,
			add 				atpLeg2,
			add 				atpLeg3,
			add 				atpLeg4,
			doit
		)

		(self
			setScript 		RoomScript
		)

		(ego 
			init,
			hide,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (and (== SND_DONE (music prevSignal))
					(== state presentsDone))
			(self cue)
		)
	)


	(method (handleEvent event)
		(cond
			((event claimed)
			)
			((and (== (event type) keyDown)
					(== (event message?) `#2))
				(TglSound)
;**					(= i (DoSound SoundOn))
;**					(DoSound SoundOn (not i))	;**	Backwards (and proud of it)
;**					(if i
;**						(SetMenu soundI #p_text {Turn On})
;**					else
;**						(SetMenu soundI #p_text {Turn Off})
;**					)
;**					(event claimed)
			)
;**				(demo
;**					(curRoom newRoom 200)
;**				)
			(else
				(if (!= sTitle (music number))
					(music
						number			sTitle,
						loop				FOREVER,
						play ,
					)
				)
				(curRoom newRoom 140)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(= cycles 2)
			)

			(startTheMusic
				(music
					stop,
					number			sFanfare,
					loop				1,
					play,
				)
				(= cycles 16)
			)

			(startDissolve
				(aLogoDissolve
					setCel		LastCel,
					setCycle		BegLoop self,
					setPri		10,
					init,
				)
				(aLogo
					setPri		8,
					init,
				)
			)

			(dissolveDone
				(aLogoDissolve
					dispose ,
				)
				(aLogo
					stopUpd,
				)
				(= cycles 16)
			)

			(startSierra
				(aSierra
					init,
					setCycle		EndLoop self,
				)
			)

			(sierraDone
				(aSierra
					stopUpd
				)
				(= cycles 16)
			)

			(startPresents
				(aPresents
					init,
					setCycle		EndLoop self,
				)
			)

			(presentsDone
				(aPresents
					stopUpd
				)
			)


			;**	Here we wait for the fanfare's SoundDone signal to continue...

			(pauseBetweenTunes
				(= cycles 12)
			)

			(changePix
				(music
					prevSignal	FALSE,
					stop,
					number		sTitle,
					loop			-1,
					play,
				)
				(aLogo 			dispose)
				(aSierra 		dispose)
				(aPresents 		dispose)
				(curRoom drawPic pTitle)
				(aThigh			init)
				(aCalf			init)
				(= cycles 2)
			)

			(andTitle
				(ShadowPrint  128  30 ltBlue MASH_18 (Format @string
																	"Leisure Suit Larry 3:"))
				(= cycles 10)
			)

			(pattiTitle1
				(ShadowPrint  146  90 ltBlue MASH_18 (Format @string
																	"Passionate Patti"))
				(= cycles 10)
			)

			(pattiTitle2
				(ShadowPrint  143 110 ltBlue MASH_18 (Format @string
																	"in Pursuit of the"))
				(= cycles 10)
			)

			(pattiTitle3
				(ShadowPrint  132 130 ltBlue MASH_18 (Format @string
																	"Pulsating Pectorals!"))
				(= cycles 10)
			)


			(pumpLeg
				(aThigh
					setCycle		Forward
				)
				(aCalf
					setCycle		Forward
				)
				(= cycles 16)
			)

			(gotoCartoon
				(curRoom newRoom 130)
			)
		)
	)
)

(instance aSierra of Prop
	(properties
		view			vSierra
		loop 			lSierra
		cel 			0
		x				234
		y				45
		priority 	1
 		cycleSpeed	1
	)
)
(instance aPresents of Prop
	(properties
		view			vSierra
		loop 			lPresents
		cel 			0
		x				237
		y				146
		priority		10
 		cycleSpeed	1
	)
)
(instance aLogo of Prop
	(properties
		view			vSierra
		loop 			lHalfDome
		cel 			0
		x				236
		y				124
	)
)
(instance aLogoDissolve of Prop
	(properties
		view			vSierra
		loop 			lDissolve
		cel 			LastCel
		x				236
		y				124
		cycleSpeed	3
	)
)

(instance aThigh of Prop
	(properties
		view			vRoom
		loop 			lThigh
		cel 			0
		x				78
		y				66
		priority		3
		cycleSpeed	1
	)
)
(instance aCalf of Prop
	(properties
		view			vRoom
		loop 			lCalf
		cel 			0
		x				66
		y				149
		priority		4
		cycleSpeed	1
	)
)
(instance atpLeg1 of PicView
	(properties
		view			vRoom
		loop 			lATPleg
		cel 			0
		x				37
		y				36
		priority 	3
	)
)
(instance atpLeg2 of PicView
	(properties
		view			vRoom
		loop 			lATPleg
		cel 			1
		x				26
		y				84
		priority 	3
	)
)
(instance atpLeg3 of PicView
	(properties
		view			vRoom
		loop 			lATPleg
		cel 			2
		x				18
		y				129
		priority 	3
	)
)
(instance atpLeg4 of PicView
	(properties
		view			vRoom
		loop 			lATPleg
		cel 			3
		x				25
		y				190
		priority 	3
	)
)

;**		The procedure "ShadowPrint" expects an x/y location; a bright
;**	color (above 7); a font; and a pointer to the string to be printed.

(procedure (ShadowPrint x y c f ptr)
	(Display ptr #p_at x 		y 			#p_font f #p_color (- c 8))
	(Display ptr #p_at (+ x 1) (+ y 1) 	#p_font f #p_color c)
)


